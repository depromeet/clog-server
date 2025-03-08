package org.depromeet.clog.server.domain.story

interface StoryRepository {

    fun save(story: Story): Story
}
