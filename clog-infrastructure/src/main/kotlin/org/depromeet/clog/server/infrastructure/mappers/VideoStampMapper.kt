package org.depromeet.clog.server.infrastructure.mappers

import org.depromeet.clog.server.domain.video.VideoStampCommand
import org.depromeet.clog.server.domain.video.VideoStampQuery
import org.depromeet.clog.server.infrastructure.video.VideoJpaRepository
import org.depromeet.clog.server.infrastructure.video.VideoStampEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class VideoStampMapper(
    private val videoJpaRepository: VideoJpaRepository
) : DomainEntityMapper<VideoStampQuery, VideoStampCommand, VideoStampEntity> {

    override fun toDomain(entity: VideoStampEntity): VideoStampQuery {
        return VideoStampQuery(
            id = entity.id,
            timeMs = entity.timeMs,
        )
    }

    override fun toEntity(domain: VideoStampCommand): VideoStampEntity {
        val videoEntity = videoJpaRepository.findByIdOrNull(domain.videoId)
            ?: throw IllegalArgumentException("Video not found")

        return VideoStampEntity(
            id = domain.id,
            video = videoEntity,
            timeMs = domain.timeMs,
        )
    }
}
