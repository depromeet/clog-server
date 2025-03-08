package org.depromeet.clog.server.api.user.presentation

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "유저 기본 조회 정보")
data class UserResponse(
    @Schema(description = "유저 ID")
    val id: Long,

    @Schema(description = "유저 이름")
    val name: String,
)
