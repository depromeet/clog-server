package org.depromeet.clog.server.api.calender.application

import org.depromeet.clog.server.domain.crag.domain.CragRepository
import org.depromeet.clog.server.domain.crag.domain.GradeRepository
import org.depromeet.clog.server.domain.story.StoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class GetCalender(
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
    ): CalenderResponse {
        val stories = storyRepository.findAllByUserIdAndDateBetween(
            userId = userId,
            startDate = LocalDate.of(year, month, 1),
            endDate = LocalDate.of(year, month, 1).plusMonths(1),
        )

        val storiesGroupedByDate = stories.groupBy { it.date }
        val numOfClimbDays = storiesGroupedByDate.size

        return CalenderResponse(
            numOfClimbDays = numOfClimbDays,
            totalDurationMs = stories.sumOf { it.totalDurationMs },
            days = storiesGroupedByDate.map { (date, stories) ->
                CalenderResponse.Day(
                    date = date,
                    stories = stories.map { story ->
                        CalenderResponse.Story(
                            id = story.id!!,
                            totalDurationMs = story.totalDurationMs,
                            cragName = cragRepository.findById(story.cragId!!)?.name,
                            problems = story.problems.let { problems ->
                                problems.groupBy { problem -> problem.gradeId }.map { (gradeId, problems) ->
                                    val grade = gradeRepository.findById(gradeId!!)
                                    CalenderResponse.Problem(
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
