package org.depromeet.clog.server.domain.video

interface VideoStampRepository {

    fun saveAll(videoStamps: List<VideoStampCommand>): List<VideoStampQuery>
    fun deleteAllByVideoId(videoId: Long)
}
