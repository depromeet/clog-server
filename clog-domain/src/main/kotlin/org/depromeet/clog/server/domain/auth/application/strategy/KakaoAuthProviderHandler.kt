package org.depromeet.clog.server.domain.auth.application.strategy

import com.auth0.jwk.JwkProviderBuilder
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import jakarta.transaction.Transactional
import org.depromeet.clog.server.domain.auth.application.TokenService
import org.depromeet.clog.server.domain.auth.application.dto.*
import org.depromeet.clog.server.domain.auth.presentation.exception.AuthException
import org.depromeet.clog.server.domain.common.ErrorCode
import org.depromeet.clog.server.domain.user.domain.Provider
import org.depromeet.clog.server.domain.user.domain.User
import org.depromeet.clog.server.domain.user.domain.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.net.URL
import java.security.interfaces.RSAPublicKey
import java.util.concurrent.TimeUnit

@Service
@Transactional
class KakaoAuthProviderHandler(
    private val tokenService: TokenService,
    private val userRepository: UserRepository,
    @Value("\${kakao.client-id}") private val kakaoClientId: String
) : AuthProviderHandler<KakaoLoginRequest> {

    override fun login(request: KakaoLoginRequest): AuthResponseDto {
        val kakaoUser = validateAndParseKakaoIdToken(request.idToken)
        val user =
            userRepository.findByLoginIdAndProviderAndIsDeletedFalse(kakaoUser.id, Provider.KAKAO)
                ?: registerNewKakaoUser(kakaoUser)
        return tokenService.generateTokens(user)
    }

    @Suppress("ThrowsCount")
    private fun validateAndParseKakaoIdToken(idToken: String): KakaoUserInfo {
        try {
            val jwksUrl = URL("https://kauth.kakao.com/.well-known/jwks.json")
            val jwkProvider = JwkProviderBuilder(jwksUrl)
                .cached(10, 24, TimeUnit.HOURS)
                .rateLimited(10, 1, TimeUnit.MINUTES)
                .build()
            val decodedJWT = JWT.decode(idToken)
            val keyId =
                decodedJWT.keyId ?: throw AuthException(ErrorCode.ID_TOKEN_VALIDATION_FAILED)
            val jwk = jwkProvider.get(keyId)
            val publicKey = jwk.publicKey as RSAPublicKey
            val algorithm: Algorithm = Algorithm.RSA256(publicKey, null)
            val verifier: JWTVerifier = JWT.require(algorithm)
                .withIssuer("https://kauth.kakao.com")
                .withAudience(kakaoClientId)
                .build()
            val verifiedJWT = verifier.verify(idToken)
            val subject =
                verifiedJWT.subject ?: throw AuthException(ErrorCode.ID_TOKEN_VALIDATION_FAILED)
            val nickname = verifiedJWT.getClaim("nickname").asString() ?: "kakaoUser"
            return KakaoUserInfo(
                id = subject,
                kakaoAccount = KakaoAccount(
                    profile = KakaoProfile(nickname = nickname)
                )
            )
        } catch (e: Exception) {
            throw AuthException(ErrorCode.ID_TOKEN_VALIDATION_FAILED, e)
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
}
