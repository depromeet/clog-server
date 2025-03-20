package org.depromeet.clog.server.infrastructure.mappers

import org.depromeet.clog.server.domain.video.VideoCommand
import org.depromeet.clog.server.domain.video.VideoQuery
import org.depromeet.clog.server.infrastructure.video.VideoEntity
import org.springframework.stereotype.Component

@Component
class VideoMapper(
    private val videoStampMapper: VideoStampMapper,
) : DomainEntityMapper<VideoQuery, VideoCommand, VideoEntity> {

    override fun toDomain(entity: VideoEntity): VideoQuery {
        return VideoQuery(
            id = entity.id!!,
            localPath = entity.localPath,
            thumbnailUrl = entity.thumbnailUrl,
            durationMs = entity.durationMs,
            stamps = entity.stamps.map { videoStampMapper.toDomain(it) },
        )
    }

    override fun toEntity(domain: VideoCommand): VideoEntity {
        return VideoEntity(
            id = domain.id,
            localPath = domain.localPath,
            thumbnailUrl = domain.thumbnailUrl,
            durationMs = domain.durationMs,
            stamps = domain.stamps.map { videoStampMapper.toEntity(it) },
        )
    }

    fun toDomainWithoutStamps(entity: VideoEntity): VideoQuery {
        return VideoQuery(
            id = entity.id!!,
            localPath = entity.localPath,
            thumbnailUrl = entity.thumbnailUrl,
            durationMs = entity.durationMs,
            stamps = emptyList()
        )
    }
}
