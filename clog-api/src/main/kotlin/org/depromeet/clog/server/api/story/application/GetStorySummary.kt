package org.depromeet.clog.server.api.story.application

import org.depromeet.clog.server.api.story.presentation.StorySummaryResponse
import org.depromeet.clog.server.domain.attempt.AttemptStatus
import org.depromeet.clog.server.domain.crag.domain.GradeRepository
import org.depromeet.clog.server.domain.story.Story
import org.depromeet.clog.server.domain.story.StoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetStorySummary(
    private val storyRepository: StoryRepository,
    private val gradeRepository: GradeRepository,
) {

    @Transactional(readOnly = true)
    operator fun invoke(storyId: Long): StorySummaryResponse {
        val story = storyRepository.findAggregate(storyId)
            ?: throw IllegalArgumentException("Story not found with id: $storyId")

        val problems = getProblems(story)

        val totalDurationMs = story.problems.flatMap { it.attempts }
            .sumOf { it.video?.durationMs ?: 0 }

        val totalCount = story.problems.sumOf { it.attempts.size }
        val totalSuccessCount = story.problems.sumOf {
            it.attempts.count { attempt -> attempt.status == AttemptStatus.SUCCESS }
        }
        val totalFailCount = calculateFailCount(story)

        return StorySummaryResponse(
            id = story.id!!,
            totalDurationMs = totalDurationMs,
            totalAttemptsCount = totalCount,
            totalSuccessCount = totalSuccessCount,
            totalFailCount = totalFailCount,
            problems = problems,
        )
    }

    private fun calculateFailCount(story: Story): Int {
        return story.problems.sumOf {
            it.attempts.count { attempt ->
                attempt.status == AttemptStatus.FAILURE
            }
        }
    }

    private fun getProblems(story: Story): List<StorySummaryResponse.Problem> {
        val problems = story.problems.map {
            val hex = if (it.gradeId != null) {
                val grade = gradeRepository.findById(it.gradeId!!)
                    ?: throw IllegalArgumentException("Grade not found with id: ${it.gradeId}")

                grade.color.hex
            } else {
                null
            }
            StorySummaryResponse.Problem(
                id = it.id!!,
                attemptCount = it.attempts.size,
                colorHex = hex,
            )
        }
        return problems
    }
}
