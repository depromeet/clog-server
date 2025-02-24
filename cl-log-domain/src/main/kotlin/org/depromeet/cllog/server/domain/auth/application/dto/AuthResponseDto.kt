package org.depromeet.cllog.server.domain.auth.application.dto

data class AuthResponseDto(
    val provider: String,
    val id: Long,
    val loginId: String,
    val accessToken: String,
    val refreshToken: String
)
