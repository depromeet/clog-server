package org.depromeet.clog.server.api.auth.controller

import org.depromeet.clog.server.api.configuration.ApiConstants.API_BASE_PATH_V1
import org.depromeet.clog.server.domain.auth.application.AuthService
import org.depromeet.clog.server.domain.auth.application.dto.AuthResponseDto
import org.depromeet.clog.server.domain.common.ApiResponse
import org.depromeet.clog.server.domain.user.infrastructure.UserRepository
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("$API_BASE_PATH_V1/auth")
class AuthController(
    private val authService: AuthService,
    private val userRepository: UserRepository
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

    @GetMapping("/test")
    fun getUserName(@RequestParam loginId: String): ApiResponse<String> {
        val user = userRepository.findByLoginId(loginId)
            .orElseThrow { RuntimeException("사용자를 찾을 수 없습니다. (loginId: $loginId)") }
        return ApiResponse.success(user.name)
    }
}

data class KakaoLoginRequest(
    val code: String,
    val codeVerifier: String
)

data class AppleLoginRequest(
    val code: String,
    val codeVerifier: String
)
