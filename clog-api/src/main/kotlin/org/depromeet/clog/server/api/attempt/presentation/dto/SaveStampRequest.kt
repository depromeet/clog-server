package org.depromeet.clog.server.api.attempt.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.depromeet.clog.server.domain.video.VideoStampCommand

@Schema(description = "영상 스탬프 요청 DTO")
data class SaveStampRequest(

    @Schema(description = "영상 스탬프 시간 (ms)", example = "12000")
    val timeMs: Long,
) {
    fun toDomain(videoId: Long): VideoStampCommand {
        return VideoStampCommand(
            videoId = videoId,
            timeMs = timeMs,
        )
    }
}
