package org.depromeet.clog.server.api.user.presentation

import io.swagger.v3.oas.annotations.media.Schema
import org.depromeet.clog.server.domain.user.domain.OtherUser

@Schema(description = "유저 기본 조회 정보")
data class UserResponse(
    @Schema(description = "유저 ID", example = "1")
    val id: Long,

    @Schema(description = "유저 이름", example = "권기준")
    val name: String? = null,

    @Schema(description = "유저 키", example = "180.0")
    val height: Double? = null,

    @Schema(description = "유저 팔 길이", example = "180.0")
    val armSpan: Double? = null,

    @Schema(description = "유저 인스타그램 URL", example = "https://www.instagram.com/kkjsw17")
    val instagramUrl: String? = null,

    @Schema(description = "팔로잉 여부 (본인인 경우, true)", example = "true")
    val isFollowing: Boolean,
) {

    companion object {
        fun from(user: OtherUser): UserResponse {
            return UserResponse(
                id = user.id,
                name = user.name,
                height = user.height,
                armSpan = user.armSpan,
                instagramUrl = user.instagramUrl,
                isFollowing = user.isFollowing,
            )
        }
    }
}
