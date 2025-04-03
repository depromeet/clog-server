package org.depromeet.clog.server.api.story.application

import org.depromeet.clog.server.api.story.presentation.UpdateStoryMemoRequest
import org.depromeet.clog.server.domain.auth.domain.ForbiddenException
import org.depromeet.clog.server.domain.story.StoryCommand
import org.depromeet.clog.server.domain.story.StoryNotFoundException
import org.depromeet.clog.server.domain.story.StoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UpdateStoryMemo(
    private val storyRepository: StoryRepository,
) {

    @Transactional
    operator fun invoke(
        userId: Long,
        storyId: Long,
        request: UpdateStoryMemoRequest,
    ) {
        val story = storyRepository.findByIdOrNull(storyId)
            ?: throw StoryNotFoundException()

        if (story.userId != userId) {
            throw ForbiddenException("User $userId is not allowed to update story $storyId")
        }

        storyRepository.save(
            StoryCommand(
                id = story.id,
                userId = story.userId,
                cragId = story.crag?.id,
                date = story.date,
                memo = request.memo,
                status = story.status,
            )
        )
    }
}
