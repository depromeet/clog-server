package org.depromeet.clog.server.domain.auth.application.dto

data class AppleLoginRequest(
    val code: String,
    val codeVerifier: String
)
