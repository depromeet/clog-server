package org.depromeet.clog.server.api.auth.presentation

import org.depromeet.clog.server.api.configuration.ApiConstants.API_BASE_PATH_V1
import org.depromeet.clog.server.api.configuration.annotation.ApiErrorCodes
import org.depromeet.clog.server.domain.auth.application.AuthService
import org.depromeet.clog.server.domain.auth.application.LogoutService
import org.depromeet.clog.server.domain.auth.application.dto.AppleLoginRequest
import org.depromeet.clog.server.domain.auth.application.dto.AuthResponseDto
import org.depromeet.clog.server.domain.auth.application.dto.KakaoLoginRequest
import org.depromeet.clog.server.domain.auth.application.dto.LocalLoginRequest
import org.depromeet.clog.server.domain.common.ClogApiResponse
import org.depromeet.clog.server.domain.common.ErrorCode
import org.depromeet.clog.server.domain.user.domain.UserContext
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("$API_BASE_PATH_V1/auth")
class AuthController(
    private val authService: AuthService,
    private val logoutService: LogoutService
) {
    @PostMapping("/kakao")
    @ApiErrorCodes([ErrorCode.TOKEN_EXPIRED])
    fun kakaoLogin(@RequestBody request: KakaoLoginRequest): ClogApiResponse<AuthResponseDto> {
        val authResponse = authService.kakaoLoginWithIdToken(request.idToken)
        return ClogApiResponse.from(authResponse)
    }

    @PostMapping("/apple")
    fun appleLogin(@RequestBody request: AppleLoginRequest): ClogApiResponse<AuthResponseDto> {
        val authResponse = authService.appleLoginWithCode(request.code, request.codeVerifier)
        return ClogApiResponse.from(authResponse)
    }

    @PostMapping("/logout")
    @ApiErrorCodes([ErrorCode.USER_NOT_FOUND])
    fun logout(userContext: UserContext): ClogApiResponse<Nothing?> {
        logoutService.logout(userContext.userId)
        return ClogApiResponse.from(null)
    }

    @Profile("dev", "local")
    @PostMapping("/local-login")
    fun localLogin(@RequestBody request: LocalLoginRequest): ClogApiResponse<AuthResponseDto> {
        val authResponse = authService.localLogin(request.loginId)
        return ClogApiResponse.from(authResponse)
    }
}
