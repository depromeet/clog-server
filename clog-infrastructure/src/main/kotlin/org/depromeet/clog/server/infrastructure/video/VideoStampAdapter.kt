package org.depromeet.clog.server.infrastructure.video

import org.depromeet.clog.server.domain.video.VideoStamp
import org.depromeet.clog.server.domain.video.VideoStampRepository
import org.springframework.stereotype.Repository

@Repository
class VideoStampAdapter(
    private val videoStampJpaRepository: VideoStampJpaRepository,
) : VideoStampRepository {

    override fun saveAll(videoStamps: List<VideoStamp>): List<VideoStamp> {
        return videoStampJpaRepository.saveAll(
            videoStamps.map { VideoStampEntity.fromDomain(it) }
        ).map { it.toDomain() }
    }
}
