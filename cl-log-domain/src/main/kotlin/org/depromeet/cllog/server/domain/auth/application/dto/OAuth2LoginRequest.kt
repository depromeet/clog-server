package org.depromeet.cllog.server.domain.auth.application.dto

data class OAuth2LoginRequest(
    val provider: String,
    val authorizationCode: String
)
