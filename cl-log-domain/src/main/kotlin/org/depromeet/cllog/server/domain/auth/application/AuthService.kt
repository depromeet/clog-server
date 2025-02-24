package org.depromeet.cllog.server.domain.auth.application

import org.depromeet.cllog.server.domain.auth.application.dto.AuthResponseDto
import org.depromeet.cllog.server.domain.auth.application.dto.KakaoUserInfo
import org.depromeet.cllog.server.domain.user.domain.Provider
import org.depromeet.cllog.server.domain.user.domain.User
import org.depromeet.cllog.server.domain.user.infrastructure.UserRepository
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.exchange
import org.springframework.web.client.RestTemplate
import java.lang.RuntimeException

@Service
class AuthService(
    private val tokenService: TokenService,
    private val userRepository: UserRepository,
    private val restTemplate: RestTemplate
) {

    /**
     * PKCE 방식: 카카오 authorization code와 code verifier를 받아 토큰 교환 및 로그인 수행
     */
    fun kakaoLoginWithCode(authorizationCode: String, codeVerifier: String): AuthResponseDto {
        // 카카오 토큰 엔드포인트에 PKCE 관련 파라미터를 포함하여 access token 요청
        val tokenResponse = requestAccessToken(authorizationCode, codeVerifier)
        val accessToken = tokenResponse["access_token"] as? String
            ?: throw RuntimeException("카카오 토큰 응답 오류")

        // 획득한 access token을 사용해 카카오 사용자 정보 조회
        val kakaoUser = getKakaoUserInfo(accessToken)

        // 기존 사용자 조회 또는 신규 사용자 등록
        val user = userRepository.findByLoginIdAndProvider(kakaoUser.id, Provider.KAKAO)
            .orElseGet { registerNewKakaoUser(kakaoUser) }

        // JWT 생성 후 반환
        return tokenService.generateTokens(user)
    }

    /**
     * 카카오 토큰 엔드포인트에 authorization code와 code verifier를 전달해 access token 획득
     */
    private fun requestAccessToken(
        authorizationCode: String,
        codeVerifier: String
    ): Map<String, Any> {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
        }

        val body = LinkedMultiValueMap<String, String>().apply {
            add("grant_type", "authorization_code")
            add("client_id", "9231afba04c6e592c4d325fb72d8c89c") // 클라이언트 아이디 (설정 값으로 외부화 권장)
            add("redirect_uri", "http://localhost:8080/login/oauth2/code/kakao") // 인가 요청 시 사용한 리다이렉트 URI
            add("code", authorizationCode)
            add("code_verifier", codeVerifier)
        }

        val requestEntity = HttpEntity(body, headers)

        val responseEntity = restTemplate.exchange<Map<String, Any>>(
            "https://kauth.kakao.com/oauth/token",
            HttpMethod.POST,
            requestEntity,
            object : ParameterizedTypeReference<Map<String, Any>>() {}
        )

        return responseEntity.body ?: throw RuntimeException("카카오 토큰 응답 없음")
    }

    /**
     * 카카오 API에서 사용자 정보 가져오기
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
     * 신규 사용자 등록
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
