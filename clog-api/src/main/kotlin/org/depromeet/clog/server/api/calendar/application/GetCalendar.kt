package org.depromeet.clog.server.api.calendar.application

import org.depromeet.clog.server.domain.crag.domain.CragRepository
import org.depromeet.clog.server.domain.crag.domain.GradeRepository
import org.depromeet.clog.server.domain.story.StoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class GetCalendar(
    private val storyRepository: StoryRepository,
    private val gradeRepository: GradeRepository,
    private val cragRepository: CragRepository,
) {

    @Suppress("MaxLineLength")
    @Transactional(readOnly = true)
    operator fun invoke(
        userId: Long,
        year: Int,
        month: Int,
    ): CalendarResponse {
        val stories = storyRepository.findAllByUserIdAndDateBetween(
            userId = userId,
            startDate = LocalDate.of(year, month, 1),
            endDate = LocalDate.of(year, month, 1).plusMonths(1),
        )

        val storiesGroupedByDate = stories.groupBy { it.date }
        val numOfClimbDays = storiesGroupedByDate.size

        return CalendarResponse(
            numOfClimbDays = numOfClimbDays,
            totalDurationMs = stories.sumOf { it.totalDurationMs },
            totalAttemptCount = stories.sumOf { it.problems.sumOf { problem -> problem.attempts.size } },
            successAttemptCount = stories.sumOf {
                it.problems.sumOf { problem -> problem.attempts.count { attempt -> attempt.isSuccess } }
            },
            failAttemptCount = stories.sumOf {
                it.problems.sumOf { problem -> problem.attempts.count { attempt -> !attempt.isSuccess } }
            },
            days = storiesGroupedByDate.map { (date, stories) ->
                CalendarResponse.Day(
                    date = date,
                    stories = stories.map { story ->
                        CalendarResponse.Story(
                            id = story.id!!,
                            totalDurationMs = story.totalDurationMs,
                            cragName = cragRepository.findById(story.cragId!!)?.name,
                            problems = story.problems.let { problems ->
                                problems.groupBy { problem -> problem.gradeId }.map { (gradeId, problems) ->
                                    val grade = gradeRepository.findById(gradeId!!)
                                    CalendarResponse.Problem(
                                        colorHex = grade?.color?.hex,
                                        count = problems.size,
                                    )
                                }
                            }
                        )
                    },
                    thumbnailUrl = stories.firstOrNull()?.problems?.firstOrNull()?.attempts?.firstOrNull()?.video?.thumbnailUrl
                )
            }
        )
    }
}
