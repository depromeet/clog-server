package org.depromeet.clog.server.api.problem.presentation

import io.swagger.v3.oas.annotations.media.Schema
import org.depromeet.clog.server.domain.problem.Problem

@Schema(description = "새로운 문제 등록")
data class ProblemRequest(

    @Schema(description = "난이도 ID", example = "1")
    val gradeId: Long? = null,
) {
    fun toDomain(storyId: Long): Problem {
        return Problem(
            storyId = storyId,
            gradeId = gradeId,
        )
    }
}
