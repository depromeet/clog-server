package org.depromeet.clog.server.api.story.application

import org.depromeet.clog.server.domain.story.StoryCommand
import org.depromeet.clog.server.domain.story.StoryNotFoundException
import org.depromeet.clog.server.domain.story.StoryRepository
import org.depromeet.clog.server.domain.story.StoryStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UpdateStoryStatus(
    private val storyRepository: StoryRepository,
) {

    @Transactional
    operator fun invoke(
        storyId: Long,
        status: StoryStatus,
    ) {
        val story = storyRepository.findByIdOrNull(storyId)
            ?: throw StoryNotFoundException("Story not found with id: $storyId")

        storyRepository.save(
            StoryCommand(
                id = story.id,
                userId = story.userId,
                cragId = story.crag?.id,
                date = story.date,
                memo = story.memo,
                status = status,
            )
        )
    }
}
