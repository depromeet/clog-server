package org.depromeet.clog.server.domain.auth.application

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import jakarta.transaction.Transactional
import org.depromeet.clog.server.domain.auth.application.dto.AuthResponseDto
import org.depromeet.clog.server.domain.auth.application.dto.LoginDetails
import org.depromeet.clog.server.domain.auth.domain.RefreshToken
import org.depromeet.clog.server.domain.auth.infrastructure.RefreshTokenRepository
import org.depromeet.clog.server.domain.auth.presentation.exception.AuthException
import org.depromeet.clog.server.domain.common.ErrorCode
import org.depromeet.clog.server.domain.user.domain.Provider
import org.depromeet.clog.server.domain.user.domain.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
@Transactional
class TokenService(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.access-token-expiration-millis}") private val accessTokenExpirationMillis: Long,
    @Value("\${jwt.refresh-token-expiration-millis}") private val refreshTokenExpirationMillis: Long,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    private val algorithm: Algorithm = Algorithm.HMAC512(secret)

    /**
     * JWT 액세스 및 리프레시 토큰 생성
     */
    fun generateTokens(user: User): AuthResponseDto {
        try {
            val userId = user.id ?: throw AuthException(
                ErrorCode.AUTHENTICATION_FAILED,
                RuntimeException("존재하지 않는 userId")
            )

            val accessToken = createToken(
                userId,
                user.loginId,
                user.provider.toString(),
                accessTokenExpirationMillis
            )
            val refreshToken = createToken(
                userId,
                user.loginId,
                user.provider.toString(),
                refreshTokenExpirationMillis
            )

            val refreshTokenValue = refreshToken.removePrefix("Bearer ")

            refreshTokenRepository.deleteByUserIdAndProvider(userId, user.provider)
            refreshTokenRepository.save(
                RefreshToken(
                    userId = userId,
                    loginId = user.loginId,
                    provider = user.provider,
                    token = refreshTokenValue
                )
            )

            return AuthResponseDto(
                provider = user.provider.toString(),
                id = userId,
                loginId = user.loginId,
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        } catch (e: Exception) {
            throw AuthException(ErrorCode.AUTHENTICATION_FAILED, e)
        }
    }

    /**
     * JWT 토큰 생성 (sub 추가)
     */
    private fun createToken(
        userId: Long,
        loginId: String,
        provider: String,
        expirationMillis: Long
    ): String {
        return "Bearer " + JWT.create()
            .withSubject(userId.toString()) // sub(Subject) 필드 추가
            .withClaim("userId", userId)
            .withClaim("loginId", loginId)
            .withClaim("provider", provider)
            .withExpiresAt(Date(System.currentTimeMillis() + expirationMillis))
            .sign(algorithm)
    }

    /**
     * JWT에서 로그인 정보 추출
     */
    @Suppress("ThrowsCount")
    fun extractLoginDetails(token: String): LoginDetails {
        try {
            val decodedJWT = JWT.require(algorithm)
                .build()
                .verify(token.removePrefix("Bearer "))

            val userId = decodedJWT.getClaim("userId").asLong()
                ?: throw AuthException(ErrorCode.TOKEN_INVALID, RuntimeException("userId 없음"))
            val loginId = decodedJWT.getClaim("loginId").asString()
                ?: throw AuthException(ErrorCode.TOKEN_INVALID, RuntimeException("loginId 없음"))
            val providerString = decodedJWT.getClaim("provider").asString()
                ?: throw AuthException(ErrorCode.TOKEN_INVALID, RuntimeException("provider 없음"))

            val provider = Provider.valueOf(providerString)

            return LoginDetails(
                userId = userId,
                loginId = loginId,
                provider = provider
            )
        } catch (e: Exception) {
            val errorCode = when (e) {
                is TokenExpiredException -> ErrorCode.TOKEN_EXPIRED
                is JWTVerificationException, is IllegalArgumentException -> ErrorCode.TOKEN_INVALID
                else -> ErrorCode.AUTHENTICATION_FAILED
            }
            throw AuthException(errorCode, e)
        }
    }
}
