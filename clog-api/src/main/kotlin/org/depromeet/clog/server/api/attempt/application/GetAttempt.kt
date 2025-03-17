package org.depromeet.clog.server.api.attempt.application

import org.depromeet.clog.server.api.attempt.presentation.dto.AttemptDetailResponse
import org.depromeet.clog.server.api.attempt.presentation.dto.GetAttemptResponse
import org.depromeet.clog.server.api.crag.presentation.dto.CragResponse
import org.depromeet.clog.server.api.grade.presentation.dto.ColorResponse
import org.depromeet.clog.server.domain.auth.domain.ForbiddenException
import org.depromeet.clog.server.domain.problem.ProblemQuery
import org.depromeet.clog.server.domain.story.StoryNotFoundException
import org.depromeet.clog.server.domain.story.StoryQuery
import org.depromeet.clog.server.domain.story.StoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetAttempt(
    private val storyRepository: StoryRepository,
) {

    @Transactional(readOnly = true)
    operator fun invoke(attemptId: Long, userId: Long): GetAttemptResponse {
        val story = fetchStory(attemptId, userId)
        val problem = fetchProblem(story, attemptId)
        val attempt = fetchAttempt(problem, attemptId)

        return GetAttemptResponse(
            storyId = story.id,
            problemId = problem.id,
            color = problem.grade?.color?.let { ColorResponse.from(it) },
            crag = story.crag?.let { CragResponse.from(it) },
            attempt = AttemptDetailResponse.from(attempt)
        )
    }

    private fun fetchStory(attemptId: Long, userId: Long): StoryQuery {
        val story = storyRepository.findByAttemptId(attemptId)
            ?: throw StoryNotFoundException("해당 시도에 대한 스토리를 찾을 수 없습니다. (attemptId: $attemptId)")

        if (story.userId != userId) {
            throw ForbiddenException("해당 시도에 대한 접근 권한이 없습니다.")
        }

        return story
    }

    private fun fetchAttempt(problem: ProblemQuery, attemptId: Long) = problem.attempts.find { it.id == attemptId }
        ?: throw IllegalArgumentException("해당 시도를 찾을 수 없습니다. (attemptId: $attemptId)")

    private fun fetchProblem(story: StoryQuery, attemptId: Long) =
        story.problems.find { it.attempts.any { attempt -> attempt.id == attemptId } }
            ?: throw IllegalArgumentException("해당 시도에 대한 문제를 찾을 수 없습니다. (attemptId: $attemptId)")
}
