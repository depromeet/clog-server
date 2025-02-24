package org.depromeet.cllog.server.api.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.depromeet.cllog.server.domain.auth.application.TokenService
import org.depromeet.cllog.server.domain.auth.application.dto.AuthResponseDto
import org.depromeet.cllog.server.domain.user.domain.User
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
@Component
class OAuth2LoginSuccessHandler(
    private val objectMapper: ObjectMapper,
    private val tokenService: TokenService
) : AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        try {
            val oAuth2User = authentication.principal as? OAuth2User ?: throw RuntimeException("인증된 사용자 정보 없음")
            println("[OAuth2LoginSuccessHandler] 로그인 성공: 사용자 인증 정보 확인 완료")

            // ✅ OAuth2User에서 사용자 정보 추출
            val kakaoId = oAuth2User.attributes["sub"]?.toString() ?: throw RuntimeException("카카오 ID 누락")
            val nickname = (oAuth2User.attributes["nickname"] as? String) ?: "카카오 유저"
            println("[OAuth2LoginSuccessHandler] 카카오 ID: $kakaoId, 닉네임: $nickname")

            // ✅ 서비스의 User 엔티티에서 사용자 찾기
            val user = tokenService.getUserByLoginId(kakaoId)
            if (user == null) {
                println("[OAuth2LoginSuccessHandler] DB에서 유저 정보를 찾을 수 없음: $kakaoId")
                throw RuntimeException("유저 정보 없음")
            }
            println("[OAuth2LoginSuccessHandler] 유저 정보 조회 완료: ${user.id}, ${user.loginId}")

            // ✅ JWT 생성
            val authResponse = tokenService.generateTokens(user)
            println("[OAuth2LoginSuccessHandler] JWT 생성 완료: AccessToken=${authResponse.accessToken}")

            val responseDto = AuthResponseDto.of(
                provider = user.provider.name,
                id = user.id,
                loginId = user.loginId,
                accessToken = authResponse.accessToken,
                refreshToken = authResponse.refreshToken
            )

            // ✅ JSON 응답 설정
            response.contentType = "application/json"
            response.characterEncoding = "UTF-8"
            response.status = HttpServletResponse.SC_OK
            objectMapper.writeValue(response.writer, responseDto)

            println("[OAuth2LoginSuccessHandler] JWT 응답 완료: $responseDto")

        } catch (e: Exception) {
            println("[OAuth2LoginSuccessHandler] 오류 발생: ${e.message}")
            e.printStackTrace()
            response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            response.writer.write("OAuth2 로그인 처리 중 오류 발생: ${e.message}")
        }
    }
}
