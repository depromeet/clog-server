package org.depromeet.clog.server.api.attempt.application

import org.depromeet.clog.server.domain.attempt.AttemptNotFoundException
import org.depromeet.clog.server.domain.attempt.AttemptRepository
import org.depromeet.clog.server.domain.problem.ProblemRepository
import org.depromeet.clog.server.domain.story.StoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeleteAttemptAndParents(
    private val attemptRepository: AttemptRepository,
    private val problemRepository: ProblemRepository,
    private val storyRepository: StoryRepository,
) {

    @Transactional
    operator fun invoke(attemptId: Long) {
        val problemId = deleteAttempt(attemptId)
        val storyId = deleteProblemIfEmpty(problemId)
        storyId?.let { deleteStoryIfEmpty(it) }
    }

    private fun deleteAttempt(attemptId: Long): Long {
        val attempt = attemptRepository.findByIdOrNull(attemptId)
            ?: throw AttemptNotFoundException()

        attemptRepository.deleteById(attemptId)
        return attempt.problemId
    }

    private fun deleteProblemIfEmpty(problemId: Long): Long? {
        val problem = problemRepository.findByIdOrNull(problemId)
            ?: throw IllegalStateException("Problem not found with id: $problemId")

        if (problem.attempts.isEmpty()) {
            problemRepository.deleteById(problemId)
            return problem.storyId
        }

        return null
    }

    private fun deleteStoryIfEmpty(storyId: Long) {
        val story = storyRepository.findByIdOrNull(storyId)
            ?: throw IllegalStateException("Story not found with id: $storyId")

        if (story.problems.isEmpty()) {
            storyRepository.deleteById(storyId)
        }
    }
}
