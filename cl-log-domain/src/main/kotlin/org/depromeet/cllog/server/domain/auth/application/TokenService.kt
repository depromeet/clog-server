package org.depromeet.cllog.server.domain.auth.application

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import org.depromeet.cllog.server.domain.auth.application.dto.AuthResponseDto
import org.depromeet.cllog.server.domain.auth.application.dto.LoginDetails
import org.depromeet.cllog.server.domain.user.domain.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.access.expirationHours}") private val accessTokenExpirationHours: Long,
    @Value("\${jwt.refresh.expirationDays}") private val refreshTokenExpirationDays: Long
) {
    private val algorithm: Algorithm = Algorithm.HMAC512(secret)

    /**
     * ✅ JWT 액세스 & 리프레시 토큰 생성
     */
    fun generateTokens(user: User): AuthResponseDto {
        return AuthResponseDto(
            provider = user.provider.toString(),
            id = user.id,
            loginId = user.loginId,
            accessToken = createToken(user.id, user.provider.toString(), accessTokenExpirationHours * 60 * 60 * 1000),
            refreshToken = createToken(user.id, user.provider.toString(), refreshTokenExpirationDays * 24 * 60 * 60 * 1000)
        )
    }

    /**
     * ✅ 액세스 토큰만 생성 (OAuth2 로그인 성공 시 사용)
     */
    fun generateAccessToken(user: User): String {
        return createToken(user.id, user.provider.toString(), accessTokenExpirationHours * 60 * 60 * 1000)
    }

    /**
     * ✅ 공통적인 토큰 생성 메서드
     */
    private fun createToken(id: Long, provider: String, expirationMillis: Long): String {
        return "Bearer " + JWT.create()
            .withClaim("id", id)
            .withClaim("provider", provider)
            .withExpiresAt(Date(System.currentTimeMillis() + expirationMillis))
            .sign(algorithm)
    }

    /**
     * ✅ JWT 토큰 검증 및 복호화
     */
    fun extractLoginDetails(token: String): LoginDetails {
        try {
            val decodedJWT = JWT.require(algorithm).build().verify(token.removePrefix("Bearer "))
            return LoginDetails(
                id = decodedJWT.getClaim("id").asLong(),
                provider = decodedJWT.getClaim("provider").asString()
            )
        } catch (e: TokenExpiredException) {
            throw RuntimeException("JWT 토큰이 만료되었습니다.")
        } catch (e: JWTVerificationException) {
            throw RuntimeException("JWT 검증 실패: 유효하지 않은 토큰입니다.")
        }
    }
}
