package org.depromeet.clog.server.api.problem.application

import org.depromeet.clog.server.api.problem.presentation.ProblemRequest
import org.depromeet.clog.server.api.problem.presentation.SaveProblemResponse
import org.depromeet.clog.server.domain.problem.ProblemRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SaveProblem(
    private val problemRepository: ProblemRepository,
) {

    @Transactional
    operator fun invoke(storyId: Long, request: ProblemRequest): SaveProblemResponse {
        val problem = problemRepository.save(
            request.toDomain(storyId)
        )
        return SaveProblemResponse(problem.id!!)
    }
}
