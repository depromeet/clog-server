package org.depromeet.clog.server.api.video.application

import org.depromeet.clog.server.api.video.presentation.VideoUpdateRequest
import org.depromeet.clog.server.domain.video.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UpdateVideo(
    private val videoRepository: VideoRepository,
    private val videoStampRepository: VideoStampRepository
) {

    @Transactional
    operator fun invoke(
        videoId: Long,
        request: VideoUpdateRequest
    ) {
        videoRepository.findByIdOrNull(videoId)?.let {
            videoRepository.save(
                VideoCommand(
                    id = it.id,
                    thumbnailUrl = request.thumbnailUrl ?: it.thumbnailUrl,
                    localPath = request.localPath ?: it.localPath,
                    durationMs = request.durationMs,
                )
            )
            videoStampRepository.deleteAllByVideoId(it.id)
            videoStampRepository.saveAll(
                request.stamps.map { stamp ->
                    VideoStampCommand(
                        videoId = it.id,
                        timeMs = stamp.timeMs,
                    )
                }
            )
        } ?: throw VideoNotFoundException()
    }
}
