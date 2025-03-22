package org.depromeet.clog.server.api.auth.application.dto.response

data class AuthResponseDto(
    val provider: String,
    val id: Long?,
    val loginId: String,
    val accessToken: String,
    val refreshToken: String
)
