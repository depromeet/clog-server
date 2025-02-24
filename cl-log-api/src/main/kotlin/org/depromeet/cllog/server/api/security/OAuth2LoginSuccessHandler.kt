package org.depromeet.cllog.server.api.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.depromeet.cllog.server.domain.auth.application.TokenService
import org.depromeet.cllog.server.domain.auth.application.dto.AuthResponseDto
import org.depromeet.cllog.server.domain.auth.presentation.exception.AuthErrorCode
import org.depromeet.cllog.server.domain.auth.presentation.exception.AuthException
import org.depromeet.cllog.server.domain.user.domain.User
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class OAuth2LoginSuccessHandler(
    private val objectMapper: ObjectMapper,
    private val tokenService: TokenService
) : AuthenticationSuccessHandler {

    private val logger = LoggerFactory.getLogger(OAuth2LoginSuccessHandler::class.java)

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        try {
            val oAuth2User = authentication.principal as? OAuth2User
                ?: throw AuthException(
                    AuthErrorCode.AUTHENTICATION_FAILED,
                    RuntimeException("인증된 사용자 정보 없음")
                )
            logger.info("로그인 성공: 사용자 인증 정보 확인 완료")

            // OAuth2User에서 사용자 정보 추출
            val kakaoId = oAuth2User.attributes["sub"]?.toString()
                ?: throw AuthException(
                    AuthErrorCode.AUTHENTICATION_FAILED,
                    RuntimeException("카카오 ID 누락")
                )
            val nickname = oAuth2User.attributes["nickname"] as? String ?: "카카오 유저"
            logger.info("카카오 ID: $kakaoId, 닉네임: $nickname")

            // 서비스의 User 엔티티에서 사용자 찾기
            val user: User = tokenService.getUserByLoginId(kakaoId)
                ?: throw AuthException(
                    AuthErrorCode.AUTHENTICATION_FAILED,
                    RuntimeException("유저 정보 없음: $kakaoId")
                )
            logger.info("유저 정보 조회 완료: ${user.id}, ${user.loginId}")

            // JWT 생성
            val authResponse: AuthResponseDto = tokenService.generateTokens(user)
            logger.info("JWT 생성 완료: AccessToken=${authResponse.accessToken}")

            // JSON 응답 생성 및 전송
            response.contentType = "application/json"
            response.characterEncoding = "UTF-8"
            response.status = HttpServletResponse.SC_OK
            objectMapper.writeValue(response.writer, authResponse)
            logger.info("JWT 응답 완료: $authResponse")

        } catch (e: AuthException) {
            logger.error("OAuth2 로그인 처리 중 AuthException 발생: ${e.message}", e)
            response.status = e.errorCode.httpStatus
            response.writer.write("OAuth2 로그인 처리 중 오류 발생: ${e.message}")
        } catch (e: Exception) {
            logger.error("OAuth2 로그인 처리 중 오류 발생: ${e.message}", e)
            response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            response.writer.write("OAuth2 로그인 처리 중 오류 발생: ${e.message}")
        }
    }
}
