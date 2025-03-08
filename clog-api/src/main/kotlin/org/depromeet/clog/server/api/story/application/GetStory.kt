package org.depromeet.clog.server.api.story.application

import org.depromeet.clog.server.api.story.presentation.StoryResponse
import org.depromeet.clog.server.domain.story.StoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetStory(
    private val storyRepository: StoryRepository,
) {

    @Transactional(readOnly = true)
    operator fun invoke(
        storyId: Long,
    ): StoryResponse {
        val story = storyRepository.findAggregate(storyId)
            ?: throw IllegalArgumentException("Story not found with id: $storyId")

        return StoryResponse.from(story)
    }
}
