package org.depromeet.clog.server.api.story.application

import org.depromeet.clog.server.domain.story.Story
import org.depromeet.clog.server.domain.story.StoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DeleteStory(
    private val storyRepository: StoryRepository
) {

    @Transactional
    operator fun invoke(
        storyId: Long,
    ) {
        val story: Story = storyRepository.findAggregate(storyId)
            ?: throw IllegalArgumentException("Story not found with id: $storyId")

        storyRepository.save(
            story.copy(userId = null)
        )
    }
}
