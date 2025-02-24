package org.depromeet.cllog.server.api.auth.controller

import org.depromeet.cllog.server.domain.auth.application.AuthService
import org.depromeet.cllog.server.domain.auth.application.dto.AuthResponseDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/kakao")
    fun kakaoLogin(@RequestBody request: KakaoLoginRequest): ResponseEntity<AuthResponseDto> {
        val authResponse = authService.kakaoLoginWithToken(request.accessToken)
        return ResponseEntity.ok(authResponse)
    }
}

data class KakaoLoginRequest(
    val accessToken: String // ✅ iOS에서 받은 accessToken을 요청 본문으로 받음
)

