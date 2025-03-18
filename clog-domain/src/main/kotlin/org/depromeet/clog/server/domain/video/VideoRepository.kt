package org.depromeet.clog.server.domain.video

interface VideoRepository {

    fun save(video: VideoCommand): VideoQuery
    fun findByIdOrNull(videoId: Long): VideoQuery?
}
