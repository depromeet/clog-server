package org.depromeet.clog.server.domain.story

import org.depromeet.clog.server.domain.crag.domain.Crag
import java.time.LocalDate

interface StoryRepository {

    fun save(story: Story): Story

    fun findByIdOrNull(storyId: Long): Story?

    fun findAggregate(storyId: Long): Story?

    fun findAllByUserIdAndDateBetween(
        userId: Long,
        startDate: LocalDate,
        endDate: LocalDate,
    ): List<Story>

    fun deleteById(storyId: Long)

    fun findDistinctCragsByUserId(userId: Long): List<Crag>
}
