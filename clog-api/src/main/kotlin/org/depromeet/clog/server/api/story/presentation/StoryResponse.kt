package org.depromeet.clog.server.api.story.presentation

import io.swagger.v3.oas.annotations.media.Schema
import org.depromeet.clog.server.api.problem.presentation.ProblemResponse
import org.depromeet.clog.server.domain.story.StoryQuery

@Schema(description = "기록 응답 DTO")
data class StoryResponse(
    @Schema(description = "기록 ID", example = "1")
    val id: Long,

    @Schema(
        description = "해당 기록의 총 클라이밍 시간 (ms)",
        example = "3600000",
    )
    val totalDurationMs: Long,

    @Schema(description = "문제 리스트")
    val problems: List<ProblemResponse>,
) {
    companion object {
        fun from(storyQuery: StoryQuery): StoryResponse {
            return StoryResponse(
                id = storyQuery.id,
                totalDurationMs = storyQuery.totalDurationMs,
                problems = storyQuery.problems.map { ProblemResponse.from(it) },
            )
        }
    }
}
