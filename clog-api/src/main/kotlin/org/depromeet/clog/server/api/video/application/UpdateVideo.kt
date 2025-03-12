package org.depromeet.clog.server.api.video.application

import org.depromeet.clog.server.api.video.presentation.VideoUpdateRequest
import org.depromeet.clog.server.domain.video.VideoNotFoundException
import org.depromeet.clog.server.domain.video.VideoRepository
import org.depromeet.clog.server.domain.video.VideoStamp
import org.depromeet.clog.server.domain.video.VideoStampRepository
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
                it.copy(
                    localPath = request.localPath ?: it.localPath,
                    thumbnailUrl = request.thumbnailUrl ?: it.thumbnailUrl,
                    durationMs = request.durationMs,
                )
            )
            videoStampRepository.deleteAllByVideoId(it.id!!)
            videoStampRepository.saveAll(
                request.stamps.map { stamp ->
                    VideoStamp(
                        videoId = it.id!!,
                        timeMs = stamp.timeMs,
                    )
                }
            )
        } ?: throw VideoNotFoundException()
    }
}
