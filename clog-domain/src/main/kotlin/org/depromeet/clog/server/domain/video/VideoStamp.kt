package org.depromeet.clog.server.domain.video

import java.time.Instant

data class VideoStamp(
    val id: Long = 0L,
    val videoId: Long,
    val timeMs: Long,
    val createdAt: Instant?,
    val updatedAt: Instant?,
)
