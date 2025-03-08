package org.depromeet.clog.server.domain.video

import java.time.Instant

data class Video(
    val id: Long = 0L,
    val localPath: String,
    val thumbnailUrl: String,
    val durationMs: Long,
    val createdAt: Instant?,
    val updatedAt: Instant?,
    val stamps: List<VideoStamp> = emptyList()
)
