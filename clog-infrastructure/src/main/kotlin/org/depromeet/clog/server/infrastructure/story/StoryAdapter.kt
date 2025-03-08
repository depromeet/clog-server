package org.depromeet.clog.server.infrastructure.story

import org.depromeet.clog.server.domain.story.Story
import org.depromeet.clog.server.domain.story.StoryRepository
import org.springframework.stereotype.Component

@Component
class StoryAdapter(
    private val storyJpaRepository: StoryJpaRepository,
) : StoryRepository {

    override fun save(story: Story): Story {
        return storyJpaRepository.save(StoryEntity.from(story)).toDomain()
    }
}
