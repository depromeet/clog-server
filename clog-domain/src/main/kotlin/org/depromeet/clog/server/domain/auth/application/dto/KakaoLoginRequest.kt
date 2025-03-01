package org.depromeet.clog.server.domain.auth.application.dto

data class KakaoLoginRequest(
    val code: String,
    val codeVerifier: String
)
