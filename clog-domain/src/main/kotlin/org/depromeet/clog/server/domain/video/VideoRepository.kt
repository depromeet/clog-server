package org.depromeet.clog.server.domain.video

interface VideoRepository {

    fun save(video: VideoCommand): VideoQuery
    fun updateThumbnailUrl(videoId: Long, thumbnailUrl: String): Int
    fun findByIdOrNull(videoId: Long): VideoQuery?
}
