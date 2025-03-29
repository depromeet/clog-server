package org.depromeet.clog.server.api.attempt.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.depromeet.clog.server.domain.video.VideoCommand

@Schema(description = "시도에 대한 영상 저장 요청")
data class SaveVideoRequest(
    @Schema(description = "영상의 앱 내 로컬 경로", example = "/2023/10/01/1234567890.mp4")
    val localPath: String,

    @Schema(description = "영상의 썸네일 URL", example = "https://example.com/thumbnail.jpg")
    val thumbnailUrl: String? = null,

    @Schema(description = "영상의 길이 (ms)", example = "3600000")
    val durationMs: Long,

    @Schema(description = "영상에 포함된 스탬프 리스트")
    val stamps: List<SaveStampRequest>,
) {
    fun toDomain(): VideoCommand {
        return VideoCommand(
            localPath = localPath,
            thumbnailUrl = thumbnailUrl,
            durationMs = durationMs,
        )
    }
}
