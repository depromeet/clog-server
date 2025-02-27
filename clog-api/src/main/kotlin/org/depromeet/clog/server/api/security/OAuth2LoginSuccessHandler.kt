package org.depromeet.clog.server.api.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.depromeet.clog.server.domain.auth.application.TokenService
import org.depromeet.clog.server.domain.auth.application.dto.AuthResponseDto
import org.depromeet.clog.server.domain.auth.presentation.exception.AuthErrorCode
import org.depromeet.clog.server.domain.auth.presentation.exception.AuthException
import org.depromeet.clog.server.domain.user.domain.User
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
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
                    errorCode = AuthErrorCode.AUTHENTICATION_FAILED,
                    cause = RuntimeException("인증된 사용자 정보 없음")
                )
            logger.info("로그인 성공: 사용자 인증 정보 확인 완료")

            // OAuth2User에서 사용자 정보 추출
            val kakaoId = oAuth2User.attributes["sub"]?.toString()
                ?: throw AuthException(
                    errorCode = AuthErrorCode.AUTHENTICATION_FAILED,
                    cause = RuntimeException("카카오 ID 누락")
                )
            val nickname = oAuth2User.attributes["nickname"] as? String ?: "카카오 유저"
            logger.info("카카오 ID: $kakaoId, 닉네임: $nickname")

            // 서비스의 User 엔티티에서 사용자 찾기
            val user: User = tokenService.getUserByLoginId(kakaoId)
                ?: throw AuthException(
                    errorCode = AuthErrorCode.AUTHENTICATION_FAILED,
                    cause = RuntimeException("유저 정보 없음: $kakaoId")
                )
            logger.info("유저 정보 조회 완료: ${user.id}, ${user.loginId}")

            // JWT 생성
            val authResponse: AuthResponseDto = tokenService.generateTokens(user)
            logger.info("JWT 생성 완료: AccessToken=${authResponse.accessToken}")

            // JSON 응답 전송
            sendResponse(response, HttpStatus.OK, authResponse)

        } catch (e: AuthException) {
            logger.error("OAuth2 로그인 처리 중 AuthException 발생: ${e.message}", e)
            sendResponse(response, HttpStatus.valueOf(e.errorCode.httpStatus), mapOf("error" to e.message))
        } catch (e: Exception) {
            logger.error("OAuth2 로그인 처리 중 오류 발생: ${e.message}", e)
            sendResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, mapOf("error" to "OAuth2 로그인 처리 중 오류 발생"))
        }
    }

    /**
     * 공통 응답 처리 메서드
     */
    private fun sendResponse(response: HttpServletResponse, status: HttpStatus, body: Any) {
        response.status = status.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = "UTF-8"
        objectMapper.writeValue(response.writer, body)
    }
}
