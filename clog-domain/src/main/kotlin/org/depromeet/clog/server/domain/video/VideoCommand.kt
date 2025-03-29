package org.depromeet.clog.server.domain.video

data class VideoCommand(
    val id: Long? = null,
    val localPath: String,
    val thumbnailUrl: String? = null,
    val durationMs: Long,
    val stamps: List<VideoStampCommand> = emptyList()
)
