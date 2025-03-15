package org.depromeet.clog.server.api.calendar.application

import org.depromeet.clog.server.api.calendar.application.CalendarResponse.Summary
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

        return CalendarResponse(
            Summary.from(stories),
            days = storiesGroupedByDate.map { (date, stories) ->
                CalendarResponse.Day(
                    date = date,
                    stories = stories.map { story ->
                        CalendarResponse.StoryListItem(
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
