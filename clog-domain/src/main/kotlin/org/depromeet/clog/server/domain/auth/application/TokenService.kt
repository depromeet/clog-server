package org.depromeet.clog.server.domain.auth.application

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import org.depromeet.clog.server.domain.auth.application.dto.AuthResponseDto
import org.depromeet.clog.server.domain.auth.application.dto.LoginDetails
import org.depromeet.clog.server.domain.auth.domain.RefreshToken
import org.depromeet.clog.server.domain.auth.infrastructure.RefreshTokenRepository
import org.depromeet.clog.server.domain.auth.presentation.exception.AuthException
import org.depromeet.clog.server.domain.common.ErrorCode
import org.depromeet.clog.server.domain.user.domain.Provider
import org.depromeet.clog.server.domain.user.domain.User
import org.depromeet.clog.server.domain.user.infrastructure.UserRepository
import org.depromeet.clog.server.domain.user.presentation.exception.UserException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.access-token-expiration-millis}") private val accessTokenExpirationMillis: Long,
    @Value("\${jwt.refresh-token-expiration-millis}") private val refreshTokenExpirationMillis: Long,
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    private val algorithm: Algorithm = Algorithm.HMAC512(secret)

    /**
     * JWT 액세스 및 리프레시 토큰 생성
     */
    fun generateTokens(user: User): AuthResponseDto {
        try {
            val accessToken = createToken(user.loginId, user.provider.toString(), accessTokenExpirationMillis)
            val refreshToken = createToken(user.loginId, user.provider.toString(), refreshTokenExpirationMillis)

            val refreshTokenValue = refreshToken.removePrefix("Bearer ")
            val userId = user.id ?: throw AuthException(
                ErrorCode.AUTHENTICATION_FAILED,
                RuntimeException("존재하지 않는 userId")
            )
            refreshTokenRepository.deleteByUserIdAndProvider(userId, user.provider)
            refreshTokenRepository.save(
                RefreshToken(
                    userId,
                    user.loginId,
                    user.provider,
                    refreshTokenValue
                )
            )

            return AuthResponseDto(
                provider = user.provider.toString(),
                id = user.id,
                loginId = user.loginId,
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        } catch (e: Exception) {
            throw AuthException(ErrorCode.AUTHENTICATION_FAILED, e)
        }
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

    @Suppress("ThrowsCount")
    fun extractLoginDetails(token: String): LoginDetails {
        try {
            val decodedJWT = JWT.require(algorithm)
                .build()
                .verify(token.removePrefix("Bearer "))

            val loginId = decodedJWT.getClaim("loginId").asString()
            val providerString = decodedJWT.getClaim("provider").asString()
                ?: throw AuthException(ErrorCode.TOKEN_INVALID)

            val provider = Provider.valueOf(providerString)

            return LoginDetails(
                loginId = loginId,
                provider = provider
            )
        } catch (e: TokenExpiredException) {
            throw AuthException(ErrorCode.TOKEN_EXPIRED, e)
        } catch (e: JWTVerificationException) {
            throw AuthException(ErrorCode.TOKEN_INVALID, e)
        } catch (e: IllegalArgumentException) {
            throw AuthException(ErrorCode.TOKEN_INVALID, e)
        }
    }

    fun logout(token: String) {
        val loginDetails = extractLoginDetails(token)

        val user = userRepository.findByLoginIdAndProvider(loginDetails.loginId, loginDetails.provider)
            ?: throw UserException(ErrorCode.USER_NOT_FOUND)

        refreshTokenRepository.deleteByUserIdAndProvider(user.id!!, user.provider)
    }
}
