package org.depromeet.clog.server.api.user.presentation

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "유저 기본 조회 정보")
data class UserResponse(
    @Schema(description = "유저 ID", example = "1")
    val id: Long,

    @Schema(description = "유저 이름", example = "권기준")
    val name: String? = null,

    @Schema(description = "유저 키", example = "180")
    val height: Int? = null,

    @Schema(description = "유저 팔 길이", example = "180")
    val armSpan: Int? = null,

    @Schema(description = "유저 인스타그램 URL", example = "https://www.instagram.com/kkjsw17")
    val instagramUrl: String? = null,
)
