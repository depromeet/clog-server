package org.depromeet.clog.server.api.auth.application

import org.depromeet.clog.server.api.auth.application.dto.request.AppleLoginRequest
import org.depromeet.clog.server.api.auth.application.dto.request.KakaoLoginRequest
import org.depromeet.clog.server.api.auth.application.dto.request.LocalLoginRequest
import org.depromeet.clog.server.api.auth.application.dto.response.AuthResponseDto
import org.depromeet.clog.server.api.auth.application.strategy.AppleAuthProviderHandler
import org.depromeet.clog.server.api.auth.application.strategy.KakaoAuthProviderHandler
import org.depromeet.clog.server.api.auth.application.strategy.LocalAuthProviderHandler
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AuthService(
    private val kakaoAuthProviderHandler: KakaoAuthProviderHandler,
    private val appleAuthProviderHandler: AppleAuthProviderHandler,
    private val localAuthProviderHandler: LocalAuthProviderHandler
) {

    fun kakaoLoginWithIdToken(idToken: String): AuthResponseDto {
        return kakaoAuthProviderHandler.login(KakaoLoginRequest(idToken))
    }

    fun appleLoginWithCode(authorizationCode: String, codeVerifier: String): AuthResponseDto {
        return appleAuthProviderHandler.login(AppleLoginRequest(authorizationCode, codeVerifier))
    }

    fun localLogin(loginId: String): AuthResponseDto {
        return localAuthProviderHandler.login(LocalLoginRequest((loginId)))
    }
}
