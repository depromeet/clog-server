package org.depromeet.clog.server.domain.video

data class VideoStampCommand(
    val id: Long? = null,
    val videoId: Long,
    val timeMs: Long,
)
