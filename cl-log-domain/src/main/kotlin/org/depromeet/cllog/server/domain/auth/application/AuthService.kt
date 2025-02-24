package org.depromeet.cllog.server.domain.auth.application

import org.depromeet.cllog.server.domain.auth.application.dto.AuthResponseDto
import org.depromeet.cllog.server.domain.auth.application.dto.KakaoUserInfo
import org.depromeet.cllog.server.domain.auth.application.dto.KakaoTokenResponse
import org.depromeet.cllog.server.domain.user.domain.Provider
import org.depromeet.cllog.server.domain.user.domain.User
import org.depromeet.cllog.server.domain.user.infrastructure.UserRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange

@Service
class AuthService(
    private val tokenService: TokenService,
    private val userRepository: UserRepository,
    private val restTemplate: RestTemplate,
    @Value("\${oauth.kakao.client-id}") private val kakaoClientId: String,
    @Value("\${oauth.kakao.redirect-uri}") private val kakaoRedirectUri: String
) {

    /**
     * 🔹 카카오 로그인 (Authorization Code를 받아 사용자 정보를 가져오고 JWT 발급)
     */
    fun kakaoLogin(authorizationCode: String): AuthResponseDto {
        val accessToken = getKakaoAccessToken(authorizationCode)
        val kakaoUser = getKakaoUserInfo(accessToken)

        val user = userRepository.findByLoginIdAndProvider(kakaoUser.id, Provider.KAKAO)
            .orElseGet { registerNewKakaoUser(kakaoUser) }

        return tokenService.generateTokens(user) // ✅ AuthResponseDto로 반환
    }

    /**
     * 🔹 카카오 OAuth2 서버에서 액세스 토큰 요청
     */
    private fun getKakaoAccessToken(authorizationCode: String): String {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
        }
        val body = "grant_type=authorization_code&client_id=$kakaoClientId&redirect_uri=$kakaoRedirectUri&code=$authorizationCode"

        val request = HttpEntity(body, headers)
        val response = restTemplate.postForEntity(
            "https://kauth.kakao.com/oauth/token",
            request,
            KakaoTokenResponse::class.java
        )

        return response.body?.accessToken ?: throw RuntimeException("카카오 액세스 토큰 요청 실패")
    }

    /**
     * 🔹 카카오 API에서 사용자 정보 가져오기
     */
    private fun getKakaoUserInfo(accessToken: String): KakaoUserInfo {
        val headers = HttpHeaders().apply {
            setBearerAuth(accessToken)
        }

        val request = HttpEntity(null, headers)
        val response = restTemplate.exchange<KakaoUserInfo>(
            "https://kapi.kakao.com/v2/user/me",
            HttpMethod.GET,
            request
        )

        return response.body ?: throw RuntimeException("카카오 사용자 정보 요청 실패")
    }

    /**
     * 🔹 신규 사용자 등록
     */
    private fun registerNewKakaoUser(kakaoUserInfo: KakaoUserInfo): User {
        val newUser = User(
            loginId = kakaoUserInfo.id.toString(),
            name = kakaoUserInfo.nickname,
            provider = Provider.KAKAO
        )
        return userRepository.save(newUser)
    }
}
