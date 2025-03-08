package org.depromeet.clog.server.domain.video

interface VideoRepository {

    fun save(video: Video): Video
}
