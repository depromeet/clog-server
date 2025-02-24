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
        // PKCE 방식: authorization code와 codeVerifier를 이용하여 토큰 교환 후 로그인 수행
        val authResponse = authService.kakaoLoginWithCode(request.code, request.codeVerifier)
        return ResponseEntity.ok(authResponse)
    }
}

data class KakaoLoginRequest(
    val code: String,        // iOS에서 받은 authorization code
    val codeVerifier: String // iOS에서 생성한 code verifier (PKCE용)
)
