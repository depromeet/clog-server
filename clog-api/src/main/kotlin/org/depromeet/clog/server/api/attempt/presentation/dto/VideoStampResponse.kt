package org.depromeet.clog.server.api.attempt.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.depromeet.clog.server.domain.video.VideoStampQuery

@Schema(description = "영상 스탬프 응답 DTO")
data class VideoStampResponse(
    @Schema(description = "영상 스탬프 ID", example = "1")
    val id: Long,

    @Schema(description = "영상 스탬프 시간 (ms)", example = "12000")
    val timeMs: Long,
) {

    companion object {
        fun from(videoStampQuery: VideoStampQuery): VideoStampResponse {
            return VideoStampResponse(
                id = videoStampQuery.id!!,
                timeMs = videoStampQuery.timeMs,
            )
        }
    }
}
