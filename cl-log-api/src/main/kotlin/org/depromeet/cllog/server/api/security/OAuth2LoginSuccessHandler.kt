package org.depromeet.cllog.server.api.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.depromeet.cllog.server.domain.auth.application.TokenService
import org.depromeet.cllog.server.domain.auth.application.dto.AuthResponseDto
import org.depromeet.cllog.server.domain.auth.domain.CustomOAuth2User
import org.depromeet.cllog.server.domain.auth.presentation.exception.InvalidLoginException
import org.depromeet.cllog.server.domain.auth.presentation.exception.UserNotFoundException
import org.depromeet.cllog.server.domain.user.domain.Provider
import org.depromeet.cllog.server.domain.user.infrastructure.UserRepository
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

/**
 *  로그인 성공 시 accessToken, refreshToken 발급 후 AuthResponseDto 응답
 * refreshToken을 DB에 저장하여 관리
 */
@Component
class OAuth2LoginSuccessHandler(
    private val tokenService: TokenService,
    private val userRepository: UserRepository,
    private val objectMapper: ObjectMapper
) : AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val principal = authentication.principal
        if (principal !is CustomOAuth2User) throw InvalidLoginException()

        val loginId = principal.getEmail() ?: throw InvalidLoginException()
        val provider = Provider.valueOf(principal.provider)

        val user = userRepository.findActiveUserByLoginIdAndProvider(loginId, provider)
            .orElseThrow { UserNotFoundException() }

        val authResponse: AuthResponseDto = tokenService.generateTokens(user)

        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"
        response.writer.write(objectMapper.writeValueAsString(authResponse))
    }
}
