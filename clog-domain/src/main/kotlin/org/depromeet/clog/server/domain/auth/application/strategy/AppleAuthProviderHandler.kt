package org.depromeet.clog.server.domain.auth.application.strategy

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import jakarta.transaction.Transactional
import org.depromeet.clog.server.domain.auth.application.TokenService
import org.depromeet.clog.server.domain.auth.application.dto.AppleUserInfo
import org.depromeet.clog.server.domain.auth.application.dto.request.AppleLoginRequest
import org.depromeet.clog.server.domain.auth.application.dto.response.AuthResponseDto
import org.depromeet.clog.server.domain.auth.presentation.exception.AuthException
import org.depromeet.clog.server.domain.common.ErrorCode
import org.depromeet.clog.server.domain.user.domain.Provider
import org.depromeet.clog.server.domain.user.domain.User
import org.depromeet.clog.server.domain.user.domain.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import java.net.URL
import java.security.KeyFactory
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.util.*
import java.util.concurrent.TimeUnit

@Suppress("LongParameterList")
@Service
@Transactional
class AppleAuthProviderHandler(
    private val tokenService: TokenService,
    private val userRepository: UserRepository,
    private val restTemplate: RestTemplate,
    @Value("\${apple.team-id}") private val appleTeamId: String,
    @Value("\${apple.client-id}") private val appleClientId: String,
    @Value("\${apple.key-id}") private val appleKeyId: String,
    @Value("\${apple.private-key}") private val applePrivateKey: String
) : AuthProviderHandler<AppleLoginRequest> {

    override fun login(request: AppleLoginRequest): AuthResponseDto {
        val tokenResponse = requestAppleAccessToken(request.code, request.codeVerifier)
        val idToken = tokenResponse["id_token"] as? String
            ?: throw AuthException(ErrorCode.ID_TOKEN_MISSING)
        val appleUser = validateAndParseAppleIdToken(idToken)
        val user =
            userRepository.findByLoginIdAndProviderAndIsDeletedFalse(appleUser.id, Provider.APPLE)
                ?: registerNewAppleUser(appleUser)
        return tokenService.generateTokens(user)
    }

    private fun requestAppleAccessToken(
        authorizationCode: String,
        codeVerifier: String
    ): Map<String, Any> {
        val headers = HttpHeaders().apply { contentType = MediaType.APPLICATION_FORM_URLENCODED }
        val body = LinkedMultiValueMap<String, String>().apply {
            add("grant_type", "authorization_code")
            add("code", authorizationCode)
            add("redirect_uri", "http://localhost:8080/login/oauth2/code/apple")
            add("client_id", appleClientId)
            add("client_secret", generateAppleClientSecret())
            add("code_verifier", codeVerifier)
        }
        val requestEntity = HttpEntity(body, headers)
        val responseEntity = restTemplate.exchange(
            "https://appleid.apple.com/auth/token",
            HttpMethod.POST,
            requestEntity,
            object : ParameterizedTypeReference<Map<String, Any>>() {}
        )
        return responseEntity.body ?: throw AuthException(ErrorCode.TOKEN_INVALID)
    }

    @Suppress("ThrowsCount")
    private fun validateAndParseAppleIdToken(idToken: String): AppleUserInfo {
        try {
            val jwksUrl = URL("https://appleid.apple.com/auth/keys")
            val jwkProvider = com.auth0.jwk.JwkProviderBuilder(jwksUrl)
                .cached(10, 24, TimeUnit.HOURS)
                .rateLimited(10, 1, TimeUnit.MINUTES)
                .build()
            val decodedJWT = JWT.decode(idToken)
            val keyId =
                decodedJWT.keyId ?: throw AuthException(ErrorCode.ID_TOKEN_VALIDATION_FAILED)
            val jwk = jwkProvider.get(keyId)
            val publicKey = jwk.publicKey as RSAPublicKey
            val algorithm = Algorithm.RSA256(publicKey, null)
            val verifier: JWTVerifier = JWT.require(algorithm)
                .withIssuer("https://appleid.apple.com")
                .withAudience(appleClientId)
                .build()
            val verifiedJWT = verifier.verify(idToken)
            val subject =
                verifiedJWT.subject ?: throw AuthException(ErrorCode.ID_TOKEN_VALIDATION_FAILED)
            val name = verifiedJWT.getClaim("name").asString() ?: "appleUser"
            return AppleUserInfo(id = subject, name = name)
        } catch (e: Exception) {
            throw AuthException(ErrorCode.ID_TOKEN_VALIDATION_FAILED, e)
        }
    }

    private fun registerNewAppleUser(appleUser: AppleUserInfo): User {
        val existingUser = userRepository.findByLoginIdAndProvider(appleUser.id, Provider.APPLE)

        return if (existingUser != null && existingUser.isDeleted) {
            existingUser.isDeleted = false
            userRepository.save(existingUser)
        } else {
            val newUser = User(
                loginId = appleUser.id,
                name = appleUser.name,
                provider = Provider.APPLE
            )
            userRepository.save(newUser)
        }
    }

    private fun generateAppleClientSecret(): String {
        val nowMillis = System.currentTimeMillis()
        val expMillis = nowMillis + 180L * 24 * 60 * 60 * 1000

        val formattedKey = "-----BEGIN PRIVATE KEY-----\n" + applePrivateKey.replace("\\\\n", "\n")
            .trim() + "\n-----END PRIVATE KEY-----"
        val decodedKey = Base64.getDecoder().decode(
            formattedKey
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replace("\\s".toRegex(), "")
        )
        val keyFactory = KeyFactory.getInstance("EC")
        val keySpec = PKCS8EncodedKeySpec(decodedKey)
        val privateKey = keyFactory.generatePrivate(keySpec)
        return Jwts.builder()
            .setHeaderParam("kid", appleKeyId)
            .setIssuer(appleTeamId)
            .setIssuedAt(Date(nowMillis))
            .setExpiration(Date(expMillis))
            .setAudience("https://appleid.apple.com")
            .setSubject(appleClientId)
            .signWith(privateKey, SignatureAlgorithm.ES256)
            .compact()
    }
}
