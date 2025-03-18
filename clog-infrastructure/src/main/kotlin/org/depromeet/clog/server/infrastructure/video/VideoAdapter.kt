package org.depromeet.clog.server.infrastructure.video

import org.depromeet.clog.server.domain.video.VideoCommand
import org.depromeet.clog.server.domain.video.VideoQuery
import org.depromeet.clog.server.domain.video.VideoRepository
import org.depromeet.clog.server.infrastructure.mappers.VideoMapper
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class VideoAdapter(
    private val videoMapper: VideoMapper,
    private val videoJpaRepository: VideoJpaRepository,
) : VideoRepository {
    override fun save(video: VideoCommand): VideoQuery {
        val entity = videoMapper.toEntity(video)
        return videoMapper.toDomain(videoJpaRepository.save(entity))
    }

    override fun findByIdOrNull(videoId: Long): VideoQuery? {
        return videoJpaRepository.findByIdOrNull(videoId)?.let {
            videoMapper.toDomain(it)
        }
    }
}
