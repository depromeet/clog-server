package org.depromeet.clog.server.domain.video

interface VideoStampRepository {

    fun saveAll(videoStamps: List<VideoStamp>): List<VideoStamp>
}
