package org.depromeet.clog.server.infrastructure.video

import org.depromeet.clog.server.domain.video.VideoStampCommand
import org.depromeet.clog.server.domain.video.VideoStampQuery
import org.depromeet.clog.server.domain.video.VideoStampRepository
import org.depromeet.clog.server.infrastructure.mappers.VideoStampMapper
import org.springframework.stereotype.Repository

@Repository
class VideoStampAdapter(
    private val videoStampMapper: VideoStampMapper,
    private val videoStampJpaRepository: VideoStampJpaRepository,
) : VideoStampRepository {

    override fun saveAll(videoStamps: List<VideoStampCommand>): List<VideoStampQuery> {
        return videoStampJpaRepository.saveAll(
            videoStamps.map { videoStampMapper.toEntity(it) }
        ).map { videoStampMapper.toDomain(it) }
    }

    override fun deleteAllByVideoId(videoId: Long) {
        return videoStampJpaRepository.deleteAllByVideoId(videoId)
    }
}
