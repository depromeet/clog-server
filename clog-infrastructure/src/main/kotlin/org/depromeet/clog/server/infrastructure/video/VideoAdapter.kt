package org.depromeet.clog.server.infrastructure.video

import org.depromeet.clog.server.domain.video.Video
import org.depromeet.clog.server.domain.video.VideoRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class VideoAdapter(
    private val videoJpaRepository: VideoJpaRepository,
) : VideoRepository {
    override fun save(video: Video): Video {
        return videoJpaRepository.save(VideoEntity.fromDomain(video)).toDomain()
    }

    override fun updateThumbnailUrl(videoId: Long, thumbnailUrl: String): Int {
        return videoJpaRepository.updateThumbnailUrl(videoId, thumbnailUrl)
    }

    override fun findByIdOrNull(videoId: Long): Video? {
        return videoJpaRepository.findByIdOrNull(videoId)?.toDomain()
    }
}
