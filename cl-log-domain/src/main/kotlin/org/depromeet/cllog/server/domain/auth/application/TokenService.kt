package org.depromeet.cllog.server.domain.auth.application

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import org.depromeet.cllog.server.domain.auth.application.dto.AuthResponseDto
import org.depromeet.cllog.server.domain.auth.application.dto.LoginDetails
import org.depromeet.cllog.server.domain.auth.presentation.exception.InvalidLoginException
import org.depromeet.cllog.server.domain.user.domain.User
import org.springframework.stereotype.Service
import java.util.*

/**
 * 📌 JWT 발급 및 검증을 담당
 * 📌 refreshToken을 DB에 저장하여 관리
 */
@Service
class TokenService {

    // ✅ 값을 고정 (환경 변수 없이 사용 가능)
    private val secret: String =
        "4AWdJqOBz6xpsN95hz1XtKGLOuwTRFbXTFgKIJAnUlFzUY75Ae4qwoYRX6zOXxQYUi-IZc3H0ArhiAwkTDIL_g"
    private val accessTokenExpirationHours: Long = 12
    private val refreshTokenExpirationDays: Long = 30

    fun generateTokens(user: User): AuthResponseDto {
        val accessToken = generateAccessToken(user.loginId, user.id, user.provider.toString())
        val refreshToken = generateRefreshToken()

        return AuthResponseDto.of(
            provider = user.provider.toString(),
            id = user.id,
            loginId = user.loginId,
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    private fun generateAccessToken(loginId: String, id: Long, provider: String): String {
        return "Bearer " + JWT.create()
            .withClaim("loginId", loginId)
            .withClaim("id", id)
            .withClaim("provider", provider)
            .withExpiresAt(Date(System.currentTimeMillis() + accessTokenExpirationHours * 1000 * 60 * 60))
            .sign(Algorithm.HMAC512(secret))
    }

    private fun generateRefreshToken(): String {
        return "Bearer " + JWT.create()
            .withExpiresAt(Date(System.currentTimeMillis() + refreshTokenExpirationDays * 1000 * 60 * 60 * 24))
            .sign(Algorithm.HMAC512(secret))
    }

    fun parseAccessToken(accessToken: String?): String? {
        return accessToken?.removePrefix("Bearer ")
    }

    fun extractLoginDetails(token: String): LoginDetails {
        val decodedJWT = JWT.decode(token)
        val loginId = decodedJWT.getClaim("loginId").asString()
        val id = decodedJWT.getClaim("id").asLong()
        val provider = decodedJWT.getClaim("provider").asString()
        return LoginDetails(loginId, id, provider)
    }

    fun verifyToken(token: String) {
        try {
            JWT.require(Algorithm.HMAC512(secret)).build().verify(token)
        } catch (e: TokenExpiredException) {
            throw InvalidLoginException()
        } catch (e: JWTVerificationException) {
            throw JWTVerificationException("JWT verification failed")
        }
    }
}
