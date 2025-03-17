package org.depromeet.clog.server.domain.story

import java.time.LocalDate

interface StoryRepository {

    fun save(story: StoryCommand): StoryQuery

    fun findByIdOrNull(storyId: Long): StoryQuery?

    fun findAllByUserIdAndDateBetween(
        userId: Long,
        startDate: LocalDate,
        endDate: LocalDate,
    ): List<StoryQuery>

    fun findByAttemptId(attemptId: Long): StoryQuery?

    fun deleteById(storyId: Long)
}
