package org.depromeet.clog.server.domain.video

data class VideoCommand(
    val id: Long? = null,
    val localPath: String,
    val thumbnailUrl: String,
    val durationMs: Long,
    val stamps: List<VideoStampCommand> = emptyList()
)
