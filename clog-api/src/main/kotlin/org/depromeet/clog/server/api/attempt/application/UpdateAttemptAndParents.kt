package org.depromeet.clog.server.api.attempt.application

import org.depromeet.clog.server.api.attempt.presentation.dto.UpdateAttemptRequest
import org.depromeet.clog.server.domain.attempt.AttemptCommand
import org.depromeet.clog.server.domain.attempt.AttemptNotFoundException
import org.depromeet.clog.server.domain.attempt.AttemptQuery
import org.depromeet.clog.server.domain.attempt.AttemptRepository
import org.depromeet.clog.server.domain.problem.ProblemCommand
import org.depromeet.clog.server.domain.problem.ProblemNotFoundException
import org.depromeet.clog.server.domain.problem.ProblemQuery
import org.depromeet.clog.server.domain.problem.ProblemRepository
import org.depromeet.clog.server.domain.report.event.AttemptUpdatedEvent
import org.depromeet.clog.server.domain.story.StoryCommand
import org.depromeet.clog.server.domain.story.StoryNotFoundException
import org.depromeet.clog.server.domain.story.StoryQuery
import org.depromeet.clog.server.domain.story.StoryRepository
import org.depromeet.clog.server.domain.user.presentation.exception.UserNotFoundException
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UpdateAttemptAndParents(
    private val attemptRepository: AttemptRepository,
    private val problemRepository: ProblemRepository,
    private val storyRepository: StoryRepository,
    private val eventPublisher: ApplicationEventPublisher
) {

    @Transactional
    operator fun invoke(attemptId: Long, request: UpdateAttemptRequest) {
        val story = fetchStory(attemptId)
        val problem = fetchProblem(story, attemptId)
        val attempt = fetchAttempt(problem, attemptId)

        updateStory(story, request)
        updateProblem(problem, story.id, request)
        updateAttempt(attempt, problem.id, request)

        eventPublisher.publishEvent(
            AttemptUpdatedEvent(
                userId = story.userId ?: throw UserNotFoundException(),
                attemptId = attempt.id
            )
        )
    }

    private fun fetchStory(attemptId: Long): StoryQuery {
        return storyRepository.findByAttemptId(attemptId)
            ?: throw StoryNotFoundException("attemptId = ${attemptId}에 해당하는 기록이 존재하지 않습니다.")
    }

    private fun fetchProblem(storyQuery: StoryQuery, attemptId: Long): ProblemQuery {
        return storyQuery.problems.firstOrNull {
            it.attempts.any { attempt -> attempt.id == attemptId }
        } ?: throw ProblemNotFoundException("attemptId = ${attemptId}에 해당하는 문제를 찾을 수 없습니다.")
    }

    private fun fetchAttempt(problemQuery: ProblemQuery, attemptId: Long): AttemptQuery {
        return problemQuery.attempts.firstOrNull {
            it.id == attemptId
        } ?: throw AttemptNotFoundException("attemptId = ${attemptId}에 해당하는 시도를 찾을 수 없습니다.")
    }

    private fun updateStory(story: StoryQuery, request: UpdateAttemptRequest) {
        request.cragId?.let {
            storyRepository.save(
                StoryCommand(
                    id = story.id,
                    userId = story.userId,
                    cragId = request.cragId,
                    date = story.date,
                    memo = story.memo,
                )
            )
        }
    }

    private fun updateProblem(problem: ProblemQuery, storyId: Long, request: UpdateAttemptRequest) {
        val updateGradeId = request.gradeId
            ?: request.gradeUnregistered?.let {
                if (it) {
                    null
                } else {
                    problem.grade?.id
                }
            }

        problemRepository.save(
            ProblemCommand(
                id = problem.id,
                storyId = storyId,
                gradeId = updateGradeId,
            )
        )
    }

    private fun updateAttempt(
        attempt: AttemptQuery,
        problemId: Long,
        request: UpdateAttemptRequest
    ) {
        request.status?.let {
            attemptRepository.save(
                AttemptCommand(
                    id = attempt.id,
                    status = request.status,
                    problemId = problemId,
                    videoId = attempt.video.id
                )
            )
        }
    }
}
