package org.depromeet.clog.server.api.problem.presentation

import io.swagger.v3.oas.annotations.media.Schema
import org.depromeet.clog.server.api.attempt.presentation.AttemptResponse
import org.depromeet.clog.server.domain.attempt.AttemptStatus
import org.depromeet.clog.server.domain.problem.Problem

@Schema(description = "문제 응답 DTO")
data class ProblemResponse(
    @Schema(description = "문제 ID", example = "1")
    val id: Long,

    @Schema(description = "문제 시도 목록")
    val attempts: List<AttemptResponse>,

    @Schema(description = "문제 성공 횟수", example = "3")
    val successCount: Int,

    @Schema(description = "문제 실패 횟수", example = "2")
    val failCount: Int,
) {
    companion object {
        fun from(problem: Problem): ProblemResponse {
            val attempts = problem.attempts.map { AttemptResponse.from(it) }
            val successCount = attempts.count { it.status == AttemptStatus.SUCCESS }
            val failCount = attempts.count { it.status == AttemptStatus.FAILURE }

            return ProblemResponse(
                id = problem.id!!,
                attempts = attempts,
                successCount = successCount,
                failCount = failCount,
            )
        }
    }
}
