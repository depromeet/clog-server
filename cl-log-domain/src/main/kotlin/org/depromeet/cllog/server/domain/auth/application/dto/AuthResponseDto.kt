package org.depromeet.cllog.server.domain.auth.application.dto

data class AuthResponseDto(
    val provider: String,
    val id: Long,
    val loginId: String,
    val accessToken: String,
    val refreshToken: String
) {
    companion object {
        fun of(
            provider: String,
            id: Long,
            loginId: String,
            accessToken: String,
            refreshToken: String
        ): AuthResponseDto {
            return AuthResponseDto(provider, id, loginId, accessToken, refreshToken)
        }
    }
}
