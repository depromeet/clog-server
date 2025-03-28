package org.depromeet.clog.server.api.attempt.application

import org.depromeet.clog.server.domain.attempt.AttemptNotFoundException
import org.depromeet.clog.server.domain.attempt.AttemptQuery
import org.depromeet.clog.server.domain.attempt.AttemptRepository
import org.depromeet.clog.server.domain.problem.ProblemNotFoundException
import org.depromeet.clog.server.domain.problem.ProblemQuery
import org.depromeet.clog.server.domain.problem.ProblemRepository
import org.depromeet.clog.server.domain.story.StoryNotFoundException
import org.depromeet.clog.server.domain.story.StoryQuery
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
        val story = fetchStory(attemptId)
        val problem = fetchProblem(story, attemptId)
        val attempt = fetchAttempt(problem, attemptId)

        deleteAttempt(attempt)
        deleteProblemIfEmpty(problem)
        deleteStoryIfEmpty(story)
    }

    private fun fetchAttempt(
        problem: ProblemQuery,
        attemptId: Long
    ): AttemptQuery {
        val attempt = problem.attempts.firstOrNull { it.id == attemptId }
            ?: throw AttemptNotFoundException("Attempt not found with id: $attemptId")
        return attempt
    }

    private fun fetchProblem(
        story: StoryQuery,
        attemptId: Long
    ): ProblemQuery {
        val problem = story.problems.firstOrNull { it.attempts.any { attempt -> attempt.id == attemptId } }
            ?: throw ProblemNotFoundException("Problem not found for attemptId: $attemptId")
        return problem
    }

    private fun fetchStory(attemptId: Long): StoryQuery {
        val story = storyRepository.findByAttemptId(attemptId)
            ?: throw StoryNotFoundException("Story not found for attemptId: $attemptId")
        return story
    }

    private fun deleteAttempt(attempt: AttemptQuery) {
        attemptRepository.deleteById(attempt.id)
    }

    private fun deleteProblemIfEmpty(problem: ProblemQuery) {
        if (problem.attempts.isEmpty()) {
            problemRepository.deleteById(problem.id)
        }
    }

    private fun deleteStoryIfEmpty(story: StoryQuery) {
        if (story.problems.isEmpty()) {
            storyRepository.deleteById(story.id)
        }
    }
}
