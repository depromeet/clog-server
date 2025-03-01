package org.depromeet.clog.server.api.auth.controller

import org.depromeet.clog.server.api.configuration.ApiConstants.API_BASE_PATH_V1
import org.depromeet.clog.server.domain.auth.application.AuthService
import org.depromeet.clog.server.domain.auth.application.dto.AppleLoginRequest
import org.depromeet.clog.server.domain.auth.application.dto.AuthResponseDto
import org.depromeet.clog.server.domain.auth.application.dto.KakaoLoginRequest
import org.depromeet.clog.server.domain.common.ApiResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("$API_BASE_PATH_V1/auth")
class AuthController(
    private val authService: AuthService,
) {
    @PostMapping("/kakao")
    fun kakaoLogin(@RequestBody request: KakaoLoginRequest): ApiResponse<AuthResponseDto> {
        val authResponse = authService.kakaoLoginWithCode(request.code, request.codeVerifier)
        return ApiResponse.success(authResponse)
    }

    @PostMapping("/apple")
    fun appleLogin(@RequestBody request: AppleLoginRequest): ApiResponse<AuthResponseDto> {
        val authResponse = authService.appleLoginWithCode(request.code, request.codeVerifier)
        return ApiResponse.success(authResponse)
    }
}
