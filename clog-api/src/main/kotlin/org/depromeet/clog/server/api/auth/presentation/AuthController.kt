package org.depromeet.clog.server.api.auth.presentation

import io.swagger.v3.oas.annotations.Operation
import org.depromeet.clog.server.api.configuration.ApiConstants.API_BASE_PATH_V1
import org.depromeet.clog.server.api.configuration.annotation.ApiErrorCodes
import org.depromeet.clog.server.domain.auth.application.AuthService
import org.depromeet.clog.server.domain.auth.application.TokenService
import org.depromeet.clog.server.domain.auth.application.dto.request.AppleLoginRequest
import org.depromeet.clog.server.domain.auth.application.dto.request.KakaoLoginRequest
import org.depromeet.clog.server.domain.auth.application.dto.request.LocalLoginRequest
import org.depromeet.clog.server.domain.auth.application.dto.request.RefreshTokenRequest
import org.depromeet.clog.server.domain.auth.application.dto.response.AuthResponseDto
import org.depromeet.clog.server.domain.common.ClogApiResponse
import org.depromeet.clog.server.domain.common.ErrorCode
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("$API_BASE_PATH_V1/auth")
class AuthController(
    private val authService: AuthService,
    private val tokenService: TokenService
) {
    @Operation(summary = "카카오 로그인")
    @PostMapping("/kakao")
    @ApiErrorCodes([ErrorCode.TOKEN_EXPIRED])
    fun kakaoLogin(@RequestBody request: KakaoLoginRequest): ClogApiResponse<AuthResponseDto> {
        val authResponse = authService.kakaoLoginWithIdToken(request.idToken)
        return ClogApiResponse.from(authResponse)
    }

    @Operation(summary = "애플 로그인")
    @PostMapping("/apple")
    fun appleLogin(@RequestBody request: AppleLoginRequest): ClogApiResponse<AuthResponseDto> {
        val authResponse = authService.appleLoginWithCode(request.code, request.codeVerifier)
        return ClogApiResponse.from(authResponse)
    }

    @Operation(summary = "액세스토큰 재발급")
    @PostMapping("/reissue/access-token")
    fun refreshToken(@RequestBody request: RefreshTokenRequest): ClogApiResponse<AuthResponseDto> {
        val response = tokenService.refreshAccessToken(request.refreshToken)
        return ClogApiResponse.from(response)
    }

    @Profile("dev", "local")
    @PostMapping("/local-login")
    fun localLogin(@RequestBody request: LocalLoginRequest): ClogApiResponse<AuthResponseDto> {
        val authResponse = authService.localLogin(request.loginId)
        return ClogApiResponse.from(authResponse)
    }
}
