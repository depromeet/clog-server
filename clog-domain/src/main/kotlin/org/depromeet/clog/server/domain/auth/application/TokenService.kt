package org.depromeet.clog.server.domain.auth.application

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import org.depromeet.clog.server.domain.auth.application.dto.AuthResponseDto
import org.depromeet.clog.server.domain.auth.application.dto.LoginDetails
import org.depromeet.clog.server.domain.auth.presentation.exception.AuthErrorCode
import org.depromeet.clog.server.domain.auth.presentation.exception.AuthException
import org.depromeet.clog.server.domain.user.domain.Provider
import org.depromeet.clog.server.domain.user.domain.User
import org.depromeet.clog.server.domain.user.infrastructure.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.access-token-expiration-millis}") private val accessTokenExpirationMillis: Long,
    @Value("\${jwt.refresh-token-expiration-millis}") private val refreshTokenExpirationMillis: Long,
    private val userRepository: UserRepository
) {
    private val algorithm: Algorithm = Algorithm.HMAC512(secret)

    /**
     * JWT 액세스 및 리프레시 토큰 생성
     */
    fun generateTokens(user: User): AuthResponseDto {
        try {
            val accessToken = createToken(user.loginId, user.provider.toString(), accessTokenExpirationMillis)
            val refreshToken = createToken(user.loginId, user.provider.toString(), refreshTokenExpirationMillis)

            return AuthResponseDto(
                provider = user.provider.toString(),
                id = user.id,
                loginId = user.loginId,
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        } catch (e: Exception) {
            throw AuthException(AuthErrorCode.AUTHENTICATION_FAILED, e)
        }
    }

    /**
     * 특정 로그인 ID를 가진 사용자를 조회하는 메서드
     */
    fun getUserByLoginId(loginId: String): User? {
        return userRepository.findByLoginIdAndProvider(loginId, Provider.KAKAO)
    }

    /**
     * JWT 토큰 생성
     */
    private fun createToken(loginId: String, provider: String, expirationMillis: Long): String {
        return "Bearer " + JWT.create()
            .withClaim("loginId", loginId)
            .withClaim("provider", provider)
            .withExpiresAt(Date(System.currentTimeMillis() + expirationMillis))
            .sign(algorithm)
    }

    fun extractLoginDetails(token: String): LoginDetails {
        try {
            val decodedJWT = JWT.require(algorithm)
                .build()
                .verify(token.removePrefix("Bearer "))

            val loginId = decodedJWT.getClaim("loginId").asString()
            val providerString = decodedJWT.getClaim("provider").asString()
                ?: throw AuthException(AuthErrorCode.TOKEN_INVALID)

            val provider = Provider.valueOf(providerString)

            return LoginDetails(
                loginId = loginId,
                provider = provider
            )
        } catch (e: TokenExpiredException) {
            throw AuthException(AuthErrorCode.TOKEN_EXPIRED, e)
        } catch (e: JWTVerificationException) {
            throw AuthException(AuthErrorCode.TOKEN_INVALID, e)
        } catch (e: IllegalArgumentException) {
            throw AuthException(AuthErrorCode.TOKEN_INVALID, e)
        }
    }
}
