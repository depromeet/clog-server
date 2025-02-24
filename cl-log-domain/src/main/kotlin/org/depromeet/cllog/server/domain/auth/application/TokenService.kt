package org.depromeet.cllog.server.domain.auth.application

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import org.depromeet.cllog.server.domain.auth.application.dto.AuthResponseDto
import org.depromeet.cllog.server.domain.auth.application.dto.LoginDetails
import org.depromeet.cllog.server.domain.user.domain.Provider
import org.depromeet.cllog.server.domain.user.domain.User
import org.depromeet.cllog.server.domain.user.infrastructure.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.access.expirationHours}") private val accessTokenExpirationHours: Long,
    @Value("\${jwt.refresh.expirationDays}") private val refreshTokenExpirationDays: Long,
    private val userRepository: UserRepository
) {
    private val algorithm: Algorithm = Algorithm.HMAC512(secret)

    /**
     * JWT 액세스 및 리프레시 토큰 생성
     */
    fun generateTokens(user: User): AuthResponseDto {
        try {
            val accessToken = createToken(
                user.id,
                user.provider.toString(),
                accessTokenExpirationHours * 60 * 60 * 1000
            )
            val refreshToken = createToken(
                user.id,
                user.provider.toString(),
                refreshTokenExpirationDays * 24 * 60 * 60 * 1000
            )

            return AuthResponseDto(
                provider = user.provider.toString(),
                id = user.id,
                loginId = user.loginId,
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        } catch (e: Exception) {
            throw RuntimeException("JWT 생성 실패", e)
        }
    }

    /**
     * 특정 로그인 ID를 가진 사용자를 조회하는 메서드
     */
    fun getUserByLoginId(loginId: String): User? {
        return userRepository.findByLoginIdAndProvider(loginId, Provider.KAKAO).orElse(null)
    }

    /**
     * JWT 토큰 생성
     */
    private fun createToken(id: Long, provider: String, expirationMillis: Long): String {
        return "Bearer " + JWT.create()
            .withClaim("id", id)
            .withClaim("provider", provider)
            .withExpiresAt(Date(System.currentTimeMillis() + expirationMillis))
            .sign(algorithm)
    }

    fun extractLoginDetails(token: String): LoginDetails {
        try {
            val decodedJWT = JWT.require(algorithm)
                .build()
                .verify(token.removePrefix("Bearer "))
            val userId = decodedJWT.getClaim("id").asLong()
            val providerString = decodedJWT.getClaim("provider").asString()
                ?: throw RuntimeException("Provider 정보가 JWT에 포함되지 않음")
            val provider = Provider.valueOf(providerString)
            return LoginDetails(
                loginId = userId.toString(),
                provider = provider
            )
        } catch (e: TokenExpiredException) {
            throw RuntimeException("JWT 토큰이 만료되었습니다.", e)
        } catch (e: JWTVerificationException) {
            throw RuntimeException("JWT 검증 실패: 유효하지 않은 토큰입니다.", e)
        } catch (e: IllegalArgumentException) {
            throw RuntimeException("JWT 파싱 오류 발생", e)
        }
    }
}
