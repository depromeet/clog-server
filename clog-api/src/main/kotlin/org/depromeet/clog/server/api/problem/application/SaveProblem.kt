package org.depromeet.clog.server.api.problem.application

import jakarta.transaction.Transactional
import org.depromeet.clog.server.api.problem.presentation.ProblemRequest
import org.depromeet.clog.server.api.problem.presentation.ProblemResponse
import org.depromeet.clog.server.domain.problem.ProblemRepository
import org.springframework.stereotype.Service

@Service
class SaveProblem(
    private val problemRepository: ProblemRepository,
) {

    @Transactional
    operator fun invoke(storyId: Long, request: ProblemRequest): ProblemResponse {
        val problem = problemRepository.save(
            request.toDomain(storyId)
        )
        return ProblemResponse(problem.id!!)
    }
}
