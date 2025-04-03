package org.depromeet.clog.server.api.auth.application

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.depromeet.clog.server.api.auth.application.dto.LoginDetails
import org.depromeet.clog.server.api.auth.application.dto.response.AuthResponseDto
import org.depromeet.clog.server.domain.auth.domain.RefreshToken
import org.depromeet.clog.server.domain.auth.infrastructure.RefreshTokenRepository
import org.depromeet.clog.server.domain.auth.presentation.exception.AuthException
import org.depromeet.clog.server.domain.common.ErrorCode
import org.depromeet.clog.server.domain.user.domain.Provider
import org.depromeet.clog.server.domain.user.domain.User
import org.depromeet.clog.server.domain.user.domain.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

private val log = KotlinLogging.logger {}

@Service
@Transactional
class TokenService(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.access-token-expiration-millis}") private val accessTokenExpirationMillis: Long,
    @Value("\${jwt.refresh-token-expiration-millis}") private val refreshTokenExpirationMillis: Long,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val userRepository: UserRepository
) {
    private val algorithm: Algorithm = Algorithm.HMAC512(secret)

    fun generateTokens(user: User): AuthResponseDto {
        val userId = user.id ?: throw AuthException(
            ErrorCode.AUTHENTICATION_FAILED,
            RuntimeException("존재하지 않는 userId")
        )

        val accessToken = generateAccessToken(user)
        val refreshToken = generateRefreshToken(user)

        saveRefreshToken(userId, user, refreshToken)

        return AuthResponseDto(
            user.provider.toString(),
            userId,
            user.loginId,
            accessToken,
            refreshToken
        )
    }

    @Suppress("ThrowsCount")
    fun refreshAccessToken(refreshToken: String): AuthResponseDto {
        val prefixRemoved = refreshToken.removePrefix("Bearer ")

        val userId = try {
            extractUserIdFromToken(prefixRemoved)
        } catch (e: AuthException) {
            log.error(e) { "리프레시 토큰 검증 실패" }
            throw e
        }

        refreshTokenRepository.findByUserId(userId)?.takeIf { it.token == prefixRemoved }
            ?: throw AuthException(ErrorCode.TOKEN_INVALID)

        val user = userRepository.findByIdAndIsDeletedFalse(userId)
            ?: throw AuthException(ErrorCode.USER_NOT_FOUND)

        val newAccessToken = generateAccessToken(user)
        val newRefreshToken = generateRefreshToken(user)

        saveRefreshToken(userId, user, newRefreshToken)

        return AuthResponseDto(
            user.provider.toString(),
            user.id!!,
            user.loginId,
            newAccessToken,
            newRefreshToken
        )
    }

    private fun generateAccessToken(user: User) = createToken(
        user.id!!,
        user.loginId,
        user.provider.toString(),
        accessTokenExpirationMillis
    )

    private fun generateRefreshToken(user: User) = createToken(
        user.id!!,
        user.loginId,
        user.provider.toString(),
        refreshTokenExpirationMillis
    )

    private fun createToken(
        userId: Long,
        loginId: String,
        provider: String,
        expirationMillis: Long
    ): String {
        return "Bearer " + JWT.create()
            .withSubject(userId.toString())
            .withClaim("userId", userId)
            .withClaim("loginId", loginId)
            .withClaim("provider", provider)
            .withExpiresAt(Date(System.currentTimeMillis() + expirationMillis))
            .sign(algorithm)
    }

    @Suppress("ThrowsCount")
    fun extractLoginDetails(token: String): LoginDetails {
        return try {
            val decodedJWT = JWT.require(algorithm).build().verify(token.removePrefix("Bearer "))
            LoginDetails(
                userId = decodedJWT.getClaim("userId").asLong()
                    ?: throw AuthException(ErrorCode.TOKEN_INVALID, RuntimeException("userId 없음")),
                loginId = decodedJWT.getClaim("loginId").asString()
                    ?: throw AuthException(ErrorCode.TOKEN_INVALID, RuntimeException("loginId 없음")),
                provider = Provider.valueOf(
                    decodedJWT.getClaim("provider").asString() ?: throw AuthException(
                        ErrorCode.TOKEN_INVALID,
                        RuntimeException("provider 없음")
                    )
                )
            )
        } catch (e: Exception) {
            log.error(e) { "로그인 정보 추출 실패" }
            throw AuthException(ErrorCode.TOKEN_INVALID, e)
        }
    }

    private fun extractUserIdFromToken(token: String): Long {
        return try {
            val prefixRemoved = token.removePrefix("Bearer ")
            JWT.decode(prefixRemoved).getClaim("userId").asLong()
                ?: throw AuthException(ErrorCode.TOKEN_INVALID)
        } catch (e: Exception) {
            log.error(e) { "토큰에서 userId 추출 실패" }
            throw when (e) {
                is TokenExpiredException -> AuthException(ErrorCode.TOKEN_EXPIRED)
                is JWTVerificationException -> AuthException(ErrorCode.TOKEN_INVALID)
                else -> AuthException(ErrorCode.AUTHENTICATION_FAILED)
            }
        }
    }

    private fun saveRefreshToken(userId: Long, user: User, refreshToken: String) {
        val refreshTokenValue = refreshToken.removePrefix("Bearer ")
        refreshTokenRepository.deleteByUserIdAndProvider(userId, user.provider)
        refreshTokenRepository.save(
            RefreshToken(
                userId,
                user.loginId,
                user.provider,
                refreshTokenValue
            )
        )
    }
}
