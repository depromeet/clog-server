package org.depromeet.clog.server.api.attempt.application

import org.depromeet.clog.server.api.attempt.presentation.dto.AttemptUpdateRequest
import org.depromeet.clog.server.domain.attempt.Attempt
import org.depromeet.clog.server.domain.attempt.AttemptNotFoundException
import org.depromeet.clog.server.domain.attempt.AttemptRepository
import org.depromeet.clog.server.domain.problem.Problem
import org.depromeet.clog.server.domain.problem.ProblemRepository
import org.depromeet.clog.server.domain.story.StoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UpdateAttemptAndParents(
    private val attemptRepository: AttemptRepository,
    private val problemRepository: ProblemRepository,
    private val storyRepository: StoryRepository,
) {

    @Transactional
    operator fun invoke(
        attemptId: Long,
        request: AttemptUpdateRequest
    ) {
        val attempt = updateAttempt(attemptId, request)
        val problem = updateProblem(attempt.problemId, request)
        updateStory(problem.storyId, request)
    }

    private fun updateAttempt(attemptId: Long, request: AttemptUpdateRequest): Attempt {
        val attempt = attemptRepository.findByIdOrNull(attemptId)
            ?: throw AttemptNotFoundException()

        attemptRepository.save(
            attempt.copy(
                status = request.status ?: attempt.status,
            )
        )
        return attempt
    }

    private fun updateProblem(problemId: Long, request: AttemptUpdateRequest): Problem {
        val problem = problemRepository.findByIdOrNull(problemId)
            ?: throw IllegalStateException("Problem not found with id: $problemId")

        problemRepository.save(
            problem.copy(
                gradeId = if (request.gradeUnregistered == true) {
                    null
                } else {
                    request.gradeId ?: problem.gradeId
                },
            )
        )
        return problem
    }

    private fun updateStory(storyId: Long, request: AttemptUpdateRequest) {
        val story = storyRepository.findByIdOrNull(storyId)
            ?: throw IllegalStateException("Story not found with id: $storyId")

        storyRepository.save(
            story.copy(
                cragId = request.cragId ?: story.cragId,
            )
        )
    }
}
