package org.depromeet.clog.server.api.attempt.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.depromeet.clog.server.domain.attempt.AttemptQuery
import org.depromeet.clog.server.domain.attempt.AttemptStatus

@Schema(description = "문제 시도 응답 DTO")
data class AttemptResponse(
    @Schema(description = "성공/실패 여부", example = "SUCCESS")
    val status: AttemptStatus,

    @Schema(description = "문제 시도 영상")
    val video: VideoResponse,
) {

    companion object {

        fun from(attemptQuery: AttemptQuery): AttemptResponse {
            return AttemptResponse(
                status = attemptQuery.status,
                video = VideoResponse.from(attemptQuery.video),
            )
        }
    }
}
