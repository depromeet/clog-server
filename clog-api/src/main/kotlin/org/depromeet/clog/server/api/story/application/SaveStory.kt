package org.depromeet.clog.server.api.story.application

import jakarta.transaction.Transactional
import org.depromeet.clog.server.api.story.presentation.SaveStoryRequest
import org.depromeet.clog.server.api.story.presentation.SaveStoryResponse
import org.depromeet.clog.server.domain.attempt.AttemptRepository
import org.depromeet.clog.server.domain.problem.ProblemRepository
import org.depromeet.clog.server.domain.story.StoryRepository
import org.depromeet.clog.server.domain.video.VideoRepository
import org.springframework.stereotype.Service

@Service
class SaveStory(
    private val storyRepository: StoryRepository,
    private val problemRepository: ProblemRepository,
    private val attemptRepository: AttemptRepository,
    private val videoRepository: VideoRepository,
) {

    @Transactional
    operator fun invoke(
        userId: Long,
        request: SaveStoryRequest,
    ): SaveStoryResponse {
        val story = storyRepository.save(
            request.toDomain(userId)
        )

        val problem = problemRepository.save(
            request.problem.toDomain(story.id!!)
        )

        val video = videoRepository.save(
            request.video.toDomain()
        )

        attemptRepository.save(
            request.attempt.toDomain(problem.id!!, video.id!!)
        )

        return SaveStoryResponse(
            storyId = story.id!!,
            problemId = problem.id!!,
        )
    }
}
