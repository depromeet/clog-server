package org.depromeet.clog.server.api.problem.presentation

import io.swagger.v3.oas.annotations.media.Schema
import org.depromeet.clog.server.domain.problem.ProblemCommand

@Schema(description = "새로운 문제 등록")
data class SaveProblemRequest(

    @Schema(description = "난이도 ID", example = "1")
    val gradeId: Long? = null,
) {
    fun toDomain(storyId: Long): ProblemCommand {
        return ProblemCommand(
            storyId = storyId,
            gradeId = gradeId,
        )
    }
}
