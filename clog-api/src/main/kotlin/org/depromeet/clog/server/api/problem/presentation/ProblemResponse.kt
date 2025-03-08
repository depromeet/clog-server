package org.depromeet.clog.server.api.problem.presentation

import org.depromeet.clog.server.api.attempt.presentation.AttemptResponse
import org.depromeet.clog.server.domain.attempt.AttemptStatus
import org.depromeet.clog.server.domain.problem.Problem

data class ProblemResponse(
    val id: Long,
    val attempts: List<AttemptResponse>,
    val successCount: Int,
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
