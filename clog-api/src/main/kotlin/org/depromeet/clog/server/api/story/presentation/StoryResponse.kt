package org.depromeet.clog.server.api.story.presentation

import io.swagger.v3.oas.annotations.media.Schema
import org.depromeet.clog.server.api.problem.presentation.ProblemResponse
import org.depromeet.clog.server.domain.story.Story

@Schema(description = "스토리 응답 DTO")
data class StoryResponse(
    @Schema(description = "스토리 ID", example = "1")
    val id: Long,

    @Schema(description = "문제 리스트")
    val problems: List<ProblemResponse>,
) {
    companion object {
        fun from(story: Story): StoryResponse {
            return StoryResponse(
                id = story.id!!,
                problems = story.problems.map { ProblemResponse.from(it) },
            )
        }
    }
}
