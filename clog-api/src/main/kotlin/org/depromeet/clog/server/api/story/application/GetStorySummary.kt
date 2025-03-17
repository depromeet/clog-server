package org.depromeet.clog.server.api.story.application

import org.depromeet.clog.server.api.story.presentation.StorySummaryResponse
import org.depromeet.clog.server.domain.attempt.AttemptStatus
import org.depromeet.clog.server.domain.crag.domain.CragRepository
import org.depromeet.clog.server.domain.story.StoryQuery
import org.depromeet.clog.server.domain.story.StoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetStorySummary(
    private val storyRepository: StoryRepository,
    private val cragRepository: CragRepository,
) {

    @Transactional(readOnly = true)
    operator fun invoke(storyId: Long): StorySummaryResponse {
        val story = storyRepository.findByIdOrNull(storyId)
            ?: throw IllegalArgumentException("Story not found with id: $storyId")

        val problems = getProblems(story)

        val totalDurationMs = story.problems.flatMap { it.attempts }
            .sumOf { it.video.durationMs }

        val totalCount = story.problems.sumOf { it.attempts.size }
        val totalSuccessCount = story.problems.sumOf {
            it.attempts.count { attempt -> attempt.status == AttemptStatus.SUCCESS }
        }
        val totalFailCount = calculateFailCount(story)
        val cragName = story.crag?.let { cragRepository.findById(it.id!!)?.name }

        return StorySummaryResponse(
            id = story.id,
            cragName = cragName,
            totalDurationMs = totalDurationMs,
            totalAttemptsCount = totalCount,
            totalSuccessCount = totalSuccessCount,
            totalFailCount = totalFailCount,
            memo = story.memo,
            problems = problems,
        )
    }

    private fun calculateFailCount(storyQuery: StoryQuery): Int {
        return storyQuery.problems.sumOf {
            it.attempts.count { attempt ->
                attempt.status == AttemptStatus.FAILURE
            }
        }
    }

    private fun getProblems(storyQuery: StoryQuery): List<StorySummaryResponse.Problem> {
        val problems = storyQuery.problems.map {
            StorySummaryResponse.Problem(
                id = it.id,
                attemptCount = it.attempts.size,
                colorHex = it.grade?.color?.hex,
            )
        }
        return problems
    }
}
