package org.depromeet.clog.server.api.story.application

import org.depromeet.clog.server.domain.story.StoryCommand
import org.depromeet.clog.server.domain.story.StoryNotFoundException
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
        val story = storyRepository.findByIdOrNull(storyId)
            ?: throw StoryNotFoundException()

        storyRepository.save(
            StoryCommand(
                id = story.id,
                userId = null,
                cragId = story.crag?.id,
                date = story.date,
                memo = story.memo,
            )
        )
    }
}
