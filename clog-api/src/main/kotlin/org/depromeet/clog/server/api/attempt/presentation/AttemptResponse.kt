package org.depromeet.clog.server.api.attempt.presentation

import io.swagger.v3.oas.annotations.media.Schema
import org.depromeet.clog.server.domain.attempt.Attempt
import org.depromeet.clog.server.domain.attempt.AttemptStatus

@Schema(description = "문제 시도 응답 DTO")
data class AttemptResponse(
    @Schema(description = "성공/실패 여부", example = "SUCCESS")
    val status: AttemptStatus,

    @Schema(description = "문제 시도 영상")
    val video: VideoResponse,
) {

    companion object {

        fun from(attempt: Attempt): AttemptResponse {
            return AttemptResponse(
                status = attempt.status,
                video = VideoResponse.from(attempt.video!!),
            )
        }
    }
}
