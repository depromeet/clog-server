package org.depromeet.clog.server.api.attempt.presentation

import io.swagger.v3.oas.annotations.media.Schema
import org.depromeet.clog.server.domain.video.VideoStamp

@Schema(description = "영상 스탬프 요청 DTO")
data class StampRequest(

    @Schema(description = "영상 스탬프 시간 (ms)", example = "12000")
    val timeMs: Long,
) {
    fun toDomain(videoId: Long): VideoStamp {
        return VideoStamp(
            videoId = videoId,
            timeMs = timeMs,
        )
    }
}
