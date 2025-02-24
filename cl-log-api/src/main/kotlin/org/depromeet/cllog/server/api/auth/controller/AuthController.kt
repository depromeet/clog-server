package org.depromeet.cllog.server.api.auth.controller

import org.depromeet.cllog.server.api.configuration.ApiConstants.API_BASE_PATH_V1
import org.depromeet.cllog.server.domain.auth.application.AuthService
import org.depromeet.cllog.server.domain.auth.application.dto.AuthResponseDto
import org.depromeet.cllog.server.domain.auth.application.dto.OAuth2LoginRequest
import org.depromeet.cllog.server.domain.common.ApiResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("$API_BASE_PATH_V1/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/kakao")
    fun kakaoLogin(@RequestBody request: OAuth2LoginRequest): ApiResponse<AuthResponseDto> {
        val response = authService.kakaoLogin(request.authorizationCode)
        return ApiResponse.success(response)
    }
}
