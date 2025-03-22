package org.depromeet.clog.server.api.auth.application.dto.request

data class AppleLoginRequest(
    val code: String,
    val codeVerifier: String
)
