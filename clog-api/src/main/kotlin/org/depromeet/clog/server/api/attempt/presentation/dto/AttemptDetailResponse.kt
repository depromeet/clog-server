package org.depromeet.clog.server.api.attempt.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.depromeet.clog.server.domain.attempt.AttemptQuery
import org.depromeet.clog.server.domain.attempt.AttemptStatus
import java.time.LocalDateTime

@Schema(description = "문제 시도 응답 DTO")
data class AttemptDetailResponse(
    @Schema(description = "성공/실패 여부", example = "SUCCESS")
    val status: AttemptStatus,

    @Schema(description = "생성일", example = "2025-03-23T01:19:00")
    val createdAt: LocalDateTime,

    @Schema(description = "문제 시도 영상")
    val video: VideoResponse,
) {

    companion object {

        fun from(attemptQuery: AttemptQuery): AttemptDetailResponse {
            return AttemptDetailResponse(
                status = attemptQuery.status,
                createdAt = attemptQuery.createdAt,
                video = VideoResponse.from(attemptQuery.video),
            )
        }
    }
}
