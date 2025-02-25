package org.depromeet.clog.server.domain.auth.application

import com.auth0.jwk.JwkProviderBuilder
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.depromeet.clog.server.domain.auth.application.dto.*
import org.depromeet.clog.server.domain.auth.presentation.exception.AuthErrorCode
import org.depromeet.clog.server.domain.auth.presentation.exception.AuthException
import org.depromeet.clog.server.domain.user.domain.Provider
import org.depromeet.clog.server.domain.user.domain.User
import org.depromeet.clog.server.domain.user.infrastructure.UserRepository
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

@Service
class AuthService(
    private val tokenService: TokenService,
    private val userRepository: UserRepository,
    private val restTemplate: RestTemplate
) {

    @Value("\${spring.security.oauth2.client.registration.kakao.client-id}")
    private lateinit var kakaoClientId: String

    @Value("\${apple.team-id}")
    private lateinit var appleTeamId: String

    @Value("\${apple.client-id}")
    private lateinit var appleClientId: String

    @Value("\${apple.key-id}")
    private lateinit var appleKeyId: String

    @Value("\${apple.private-key}")
    private lateinit var applePrivateKey: String

    // ---------------- Kakao 로그인 ----------------

    fun kakaoLoginWithCode(authorizationCode: String, codeVerifier: String): AuthResponseDto {
        val tokenResponse = requestAccessToken(authorizationCode, codeVerifier)
        val idToken = tokenResponse["id_token"] as? String
            ?: throw AuthException(AuthErrorCode.ID_TOKEN_MISSING)
        val kakaoUser = validateAndParseKakaoIdToken(idToken)
        val user = userRepository.findByLoginIdAndProvider(kakaoUser.id, Provider.KAKAO)
            .orElseGet { registerNewKakaoUser(kakaoUser) }
        return tokenService.generateTokens(user)
    }

    private fun requestAccessToken(
        authorizationCode: String,
        codeVerifier: String
    ): Map<String, Any> {
        val headers = HttpHeaders().apply { contentType = MediaType.APPLICATION_FORM_URLENCODED }
        val body = LinkedMultiValueMap<String, String>().apply {
            add("grant_type", "authorization_code")
            add("client_id", kakaoClientId)
            add("redirect_uri", "http://localhost:8080/login/oauth2/code/kakao")
            add("code", authorizationCode)
            add("code_verifier", codeVerifier)
        }
        val requestEntity = HttpEntity(body, headers)
        val responseEntity = restTemplate.exchange(
            "https://kauth.kakao.com/oauth/token",
            HttpMethod.POST,
            requestEntity,
            object : ParameterizedTypeReference<Map<String, Any>>() {}
        )
        return responseEntity.body ?: throw AuthException(AuthErrorCode.TOKEN_INVALID)
    }

    private fun validateAndParseKakaoIdToken(idToken: String): KakaoUserInfo {
        try {
            val jwksUrl = URL("https://kauth.kakao.com/.well-known/jwks.json")
            val jwkProvider = JwkProviderBuilder(jwksUrl)
                .cached(10, 24, TimeUnit.HOURS)
                .rateLimited(10, 1, TimeUnit.MINUTES)
                .build()
            val decodedJWT = JWT.decode(idToken)
            val keyId =
                decodedJWT.keyId ?: throw AuthException(AuthErrorCode.ID_TOKEN_VALIDATION_FAILED)
            val jwk = jwkProvider.get(keyId)
            val publicKey = jwk.publicKey as RSAPublicKey
            val algorithm = Algorithm.RSA256(publicKey, null)
            val verifier: JWTVerifier = JWT.require(algorithm)
                .withIssuer("https://kauth.kakao.com")
                .withAudience(kakaoClientId)
                .build()
            val verifiedJWT = verifier.verify(idToken)
            val subject =
                verifiedJWT.subject ?: throw AuthException(AuthErrorCode.ID_TOKEN_VALIDATION_FAILED)
            val nickname = verifiedJWT.getClaim("nickname").asString() ?: "kakaoUser"
            return KakaoUserInfo(
                id = subject,
                kakaoAccount = KakaoAccount(
                    profile = KakaoProfile(nickname = nickname)
                )
            )
        } catch (e: Exception) {
            throw AuthException(AuthErrorCode.ID_TOKEN_VALIDATION_FAILED, e)
        }
    }

    private fun registerNewKakaoUser(kakaoUserInfo: KakaoUserInfo): User {
        val newUser = User(
            loginId = kakaoUserInfo.id,
            name = kakaoUserInfo.nickname,
            provider = Provider.KAKAO
        )
        return userRepository.save(newUser)
    }

    // ---------------- Apple 로그인 ----------------

    fun appleLoginWithCode(authorizationCode: String, codeVerifier: String): AuthResponseDto {
        val tokenResponse = requestAppleAccessToken(authorizationCode, codeVerifier)
        val idToken = tokenResponse["id_token"] as? String
            ?: throw AuthException(AuthErrorCode.ID_TOKEN_MISSING)
        val appleUser = validateAndParseAppleIdToken(idToken)
        val user = userRepository.findByLoginIdAndProvider(appleUser.id, Provider.APPLE)
            .orElseGet { registerNewAppleUser(appleUser) }
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
        return responseEntity.body ?: throw AuthException(AuthErrorCode.TOKEN_INVALID)
    }

    private fun validateAndParseAppleIdToken(idToken: String): AppleUserInfo {
        try {
            val jwksUrl = URL("https://appleid.apple.com/auth/keys")
            val jwkProvider = JwkProviderBuilder(jwksUrl)
                .cached(10, 24, TimeUnit.HOURS)
                .rateLimited(10, 1, TimeUnit.MINUTES)
                .build()
            val decodedJWT = JWT.decode(idToken)
            val keyId =
                decodedJWT.keyId ?: throw AuthException(AuthErrorCode.ID_TOKEN_VALIDATION_FAILED)
            val jwk = jwkProvider.get(keyId)
            val publicKey = jwk.publicKey as RSAPublicKey
            val algorithm = Algorithm.RSA256(publicKey, null)
            val verifier: JWTVerifier = JWT.require(algorithm)
                .withIssuer("https://appleid.apple.com")
                .withAudience(appleClientId)
                .build()
            val verifiedJWT = verifier.verify(idToken)
            val subject =
                verifiedJWT.subject ?: throw AuthException(AuthErrorCode.ID_TOKEN_VALIDATION_FAILED)
            val name = verifiedJWT.getClaim("name").asString() ?: "appleUser"
            return AppleUserInfo(id = subject, name = name)
        } catch (e: Exception) {
            throw AuthException(AuthErrorCode.ID_TOKEN_VALIDATION_FAILED, e)
        }
    }

    private fun registerNewAppleUser(appleUser: AppleUserInfo): User {
        val newUser = User(
            loginId = appleUser.id,
            name = appleUser.name,
            provider = Provider.APPLE
        )
        return userRepository.save(newUser)
    }

    // Apple 클라이언트 시크릿 생성
    private fun generateAppleClientSecret(): String {
        val nowMillis = System.currentTimeMillis()
        val expMillis = nowMillis + 180L * 24 * 60 * 60 * 1000

        val formattedKey = "-----BEGIN PRIVATE KEY-----\n" +
            applePrivateKey.replace("\\\\n", "\n").trim() +
            "\n-----END PRIVATE KEY-----"
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
