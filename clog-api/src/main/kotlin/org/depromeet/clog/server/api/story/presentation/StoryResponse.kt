package org.depromeet.clog.server.api.story.presentation

import org.depromeet.clog.server.api.problem.presentation.ProblemResponse
import org.depromeet.clog.server.domain.story.Story

data class StoryResponse(
    val id: Long,
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
