package org.depromeet.clog.server.api.auth.controller

import org.depromeet.clog.server.api.configuration.ApiConstants.API_BASE_PATH_V1
import org.depromeet.clog.server.domain.auth.application.AuthService
import org.depromeet.clog.server.domain.auth.application.TokenService
import org.depromeet.clog.server.domain.auth.application.dto.AppleLoginRequest
import org.depromeet.clog.server.domain.auth.application.dto.AuthResponseDto
import org.depromeet.clog.server.domain.auth.application.dto.KakaoLoginRequest
import org.depromeet.clog.server.domain.common.ApiResponse
import org.depromeet.clog.server.domain.user.infrastructure.UserRepository
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("$API_BASE_PATH_V1/auth")
class AuthController(
    private val authService: AuthService,
    private val userRepository: UserRepository,
    private val tokenService: TokenService
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

    @Profile("dev", "local")
    @GetMapping("/test")
    fun getCurrentUser(@RequestHeader(HttpHeaders.AUTHORIZATION) token: String): ApiResponse<String> {
        val loginDetails = tokenService.extractLoginDetails(token)
        val user = userRepository.findByLoginIdAndProvider(loginDetails.loginId, loginDetails.provider)
            ?: throw RuntimeException("사용자를 찾을 수 없습니다. (loginId: ${loginDetails.loginId})")

        return ApiResponse.success(user.name)
    }
}
