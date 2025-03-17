package org.depromeet.clog.server.api.story.application

import org.depromeet.clog.server.api.story.presentation.SaveStoryRequest
import org.depromeet.clog.server.api.story.presentation.SaveStoryResponse
import org.depromeet.clog.server.domain.attempt.AttemptRepository
import org.depromeet.clog.server.domain.problem.ProblemRepository
import org.depromeet.clog.server.domain.story.StoryRepository
import org.depromeet.clog.server.domain.video.VideoRepository
import org.depromeet.clog.server.domain.video.VideoStampRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SaveStory(
    private val storyRepository: StoryRepository,
    private val problemRepository: ProblemRepository,
    private val attemptRepository: AttemptRepository,
    private val videoRepository: VideoRepository,
    private val videoStampRepository: VideoStampRepository,
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
            request.problem.toDomain(story.id)
        )

        val video = videoRepository.save(
            request.attempt.video.toDomain()
        )

        videoStampRepository.saveAll(
            request.attempt.video.stamps.map { it.toDomain(video.id) }
        )

        attemptRepository.save(
            request.attempt.toDomain(video.id, problem.id)
        )

        return SaveStoryResponse(
            storyId = story.id,
            problemId = problem.id,
        )
    }
}
