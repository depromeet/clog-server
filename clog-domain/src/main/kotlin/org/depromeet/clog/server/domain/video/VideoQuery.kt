package org.depromeet.clog.server.domain.video

data class VideoQuery(
    val id: Long,
    val localPath: String,
    val thumbnailUrl: String? = null,
    val durationMs: Long,
    val stamps: List<VideoStampQuery> = emptyList(),
)
