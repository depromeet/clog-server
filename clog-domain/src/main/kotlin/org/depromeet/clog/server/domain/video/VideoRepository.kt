package org.depromeet.clog.server.domain.video

interface VideoRepository {

    fun save(video: Video): Video
    fun updateThumbnailUrl(videoId: Long, thumbnailUrl: String): Int
    fun findByIdOrNull(videoId: Long): Video?
}
