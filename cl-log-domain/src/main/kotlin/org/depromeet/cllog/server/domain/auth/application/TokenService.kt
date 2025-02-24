package org.depromeet.cllog.server.domain.auth.application

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import org.depromeet.cllog.server.domain.auth.application.dto.AuthResponseDto
import org.depromeet.cllog.server.domain.auth.application.dto.LoginDetails
import org.depromeet.cllog.server.domain.user.domain.Provider
import org.depromeet.cllog.server.domain.user.domain.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

import org.depromeet.cllog.server.domain.user.infrastructure.UserRepository

@Service
class TokenService(
    @Value("\${jwt.secret}") private val secret: String,
    @Value("\${jwt.access.expirationHours}") private val accessTokenExpirationHours: Long,
    @Value("\${jwt.refresh.expirationDays}") private val refreshTokenExpirationDays: Long,
    private val userRepository: UserRepository
) {
    private val algorithm: Algorithm = Algorithm.HMAC512(secret)

    /**
     * JWT 액세스 & 리프레시 토큰 생성
     */
    fun generateTokens(user: User): AuthResponseDto {
        try {
            println("[TokenService] JWT 생성 시작 - User ID: ${user.id}, Login ID: ${user.loginId}")

            val accessToken = createToken(user.id, user.provider.toString(), accessTokenExpirationHours * 60 * 60 * 1000)
            val refreshToken = createToken(user.id, user.provider.toString(), refreshTokenExpirationDays * 24 * 60 * 60 * 1000)

            println("[TokenService] JWT 생성 완료 - AccessToken: $accessToken")

            return AuthResponseDto(
                provider = user.provider.toString(),
                id = user.id,
                loginId = user.loginId,
                accessToken = accessToken,
                refreshToken = refreshToken
            )
        } catch (e: Exception) {
            println("[TokenService] JWT 생성 중 오류 발생: ${e.message}")
            e.printStackTrace()
            throw RuntimeException("JWT 생성 실패")
        }
    }

    /**
     * 특정 로그인 ID를 가진 사용자를 조회하는 메서드
     */
    fun getUserByLoginId(loginId: String): User? {
        println("[TokenService] DB에서 사용자 조회: loginId=$loginId")
        return userRepository.findByLoginIdAndProvider(loginId, Provider.KAKAO).orElse(null)
    }

    /**
     * JWT 토큰 생성
     */
    private fun createToken(id: Long, provider: String, expirationMillis: Long): String {
        try {
            println("[TokenService] JWT 생성 - ID: $id, Provider: $provider")
            return "Bearer " + JWT.create()
                .withClaim("id", id)
                .withClaim("provider", provider)
                .withExpiresAt(Date(System.currentTimeMillis() + expirationMillis))
                .sign(algorithm)
        } catch (e: Exception) {
            println("[TokenService] JWT 생성 실패: ${e.message}")
            e.printStackTrace()
            throw RuntimeException("JWT 생성 중 오류 발생")
        }
    }

    fun extractLoginDetails(token: String): LoginDetails {
        try {
            println("[TokenService] JWT 검증 및 복호화 시작")

            val decodedJWT = JWT.require(algorithm)
                .build()
                .verify(token.removePrefix("Bearer "))

            val userId = decodedJWT.getClaim("id").asLong()
            val providerString = decodedJWT.getClaim("provider").asString()

            if (providerString == null) {
                throw RuntimeException("Provider 정보가 JWT에 포함되지 않음")
            }

            val provider = Provider.valueOf(providerString)

            println("[TokenService] JWT 복호화 성공 - User ID: $userId, Provider: $provider")

            return LoginDetails(
                loginId = userId.toString(),
                provider = provider
            )
        } catch (e: TokenExpiredException) {
            println("[TokenService] JWT 토큰 만료: ${e.message}")
            throw RuntimeException("JWT 토큰이 만료되었습니다.")
        } catch (e: JWTVerificationException) {
            println("[TokenService] JWT 검증 실패: ${e.message}")
            throw RuntimeException("JWT 검증 실패: 유효하지 않은 토큰입니다.")
        } catch (e: IllegalArgumentException) {
            println("[TokenService] JWT 파싱 오류: ${e.message}")
            throw RuntimeException("JWT 파싱 오류 발생")
        }
}}

