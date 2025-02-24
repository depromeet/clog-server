package org.depromeet.cllog.server.domain.auth.application

import org.depromeet.cllog.server.domain.auth.application.dto.AuthResponseDto
import org.depromeet.cllog.server.domain.auth.application.dto.KakaoUserInfo
import org.depromeet.cllog.server.domain.user.domain.Provider
import org.depromeet.cllog.server.domain.user.domain.User
import org.depromeet.cllog.server.domain.user.infrastructure.UserRepository
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange

@Service
class AuthService(
    private val tokenService: TokenService,
    private val userRepository: UserRepository,
    private val restTemplate: RestTemplate
) {

    /**
     * ✅ 카카오 `accessToken`을 받아서 사용자 정보를 조회하고 JWT 발급
     */
    fun kakaoLoginWithToken(accessToken: String): AuthResponseDto {
        val kakaoUser = getKakaoUserInfo(accessToken)

        val user = userRepository.findByLoginIdAndProvider(kakaoUser.id, Provider.KAKAO)
            .orElseGet { registerNewKakaoUser(kakaoUser) }

        return tokenService.generateTokens(user) // ✅ JWT 발급 후 반환
    }

    /**
     * ✅ 카카오 API에서 사용자 정보 가져오기
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
     * ✅ 신규 사용자 등록
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
