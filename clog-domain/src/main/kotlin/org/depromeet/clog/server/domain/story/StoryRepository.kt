package org.depromeet.clog.server.domain.story

interface StoryRepository {

    fun save(story: Story): Story

    fun findByIdOrNull(storyId: Long): Story?

    fun findAggregate(storyId: Long): Story?
}
