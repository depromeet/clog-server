package org.depromeet.clog.server.domain.auth.application

import org.depromeet.clog.server.domain.auth.application.dto.AppleLoginRequest
import org.depromeet.clog.server.domain.auth.application.dto.AuthResponseDto
import org.depromeet.clog.server.domain.auth.application.dto.KakaoLoginRequest
import org.depromeet.clog.server.domain.auth.application.strategy.AppleAuthProviderHandler
import org.depromeet.clog.server.domain.auth.application.strategy.KakaoAuthProviderHandler
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val kakaoAuthProviderHandler: KakaoAuthProviderHandler,
    private val appleAuthProviderHandler: AppleAuthProviderHandler
) {
    fun kakaoLoginWithIdToken(idToken: String): AuthResponseDto {
        return kakaoAuthProviderHandler.login(KakaoLoginRequest(idToken))
    }

    fun appleLoginWithCode(authorizationCode: String, codeVerifier: String): AuthResponseDto {
        return appleAuthProviderHandler.login(AppleLoginRequest(authorizationCode, codeVerifier))
    }
}
