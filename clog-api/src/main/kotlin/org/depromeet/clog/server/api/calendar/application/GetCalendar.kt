package org.depromeet.clog.server.api.calendar.application

import org.depromeet.clog.server.api.calendar.application.CalendarResponse.Summary
import org.depromeet.clog.server.domain.story.StoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class GetCalendar(
    private val storyRepository: StoryRepository,
) {

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

        return CalendarResponse(
            Summary.from(stories),
            days = stories.groupBy { it.date }.map { (_, stories) ->
                CalendarResponse.Day.from(stories)
            }
        )
    }
}
