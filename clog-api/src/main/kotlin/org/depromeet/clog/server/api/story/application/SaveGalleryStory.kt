package org.depromeet.clog.server.api.story.application

import org.depromeet.clog.server.api.story.presentation.SaveGalleryStoryRequest
import org.depromeet.clog.server.api.story.presentation.SaveGalleryStoryResponse
import org.depromeet.clog.server.domain.attempt.AttemptCommand
import org.depromeet.clog.server.domain.attempt.AttemptRepository
import org.depromeet.clog.server.domain.attempt.AttemptStatus
import org.depromeet.clog.server.domain.problem.ProblemCommand
import org.depromeet.clog.server.domain.problem.ProblemRepository
import org.depromeet.clog.server.domain.story.StoryCommand
import org.depromeet.clog.server.domain.story.StoryRepository
import org.depromeet.clog.server.domain.story.StoryStatus
import org.depromeet.clog.server.domain.video.VideoCommand
import org.depromeet.clog.server.domain.video.VideoRepository
import org.depromeet.clog.server.domain.video.VideoStampRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SaveGalleryStory(
    private val storyRepository: StoryRepository,
    private val problemRepository: ProblemRepository,
    private val attemptRepository: AttemptRepository,
    private val videoRepository: VideoRepository,
    private val videoStampRepository: VideoStampRepository
) {
    operator fun invoke(userId: Long, request: SaveGalleryStoryRequest): SaveGalleryStoryResponse {
        val story = storyRepository.save(
            StoryCommand(
                userId = userId,
                cragId = request.cragId,
                date = request.date,
                memo = request.memo,
                status = StoryStatus.IN_PROGRESS
            )
        )

        val problem = problemRepository.save(
            ProblemCommand(
                storyId = story.id,
                gradeId = null
            )
        )

        request.videos.forEach { videoReq ->
            val video = videoRepository.save(
                VideoCommand(
                    durationMs = videoReq.durationMs,
                    thumbnailUrl = videoReq.thumbnailUrl,
                    localPath = videoReq.localPath
                )
            )

            videoStampRepository.saveAll(
                videoReq.stamps.map { it.toDomain(video.id) }
            )
            attemptRepository.save(
                AttemptCommand(
                    problemId = problem.id,
                    videoId = video.id,
                    status = AttemptStatus.UNKNOWN
                )
            )
        }

        return SaveGalleryStoryResponse(story.id)
    }
}
