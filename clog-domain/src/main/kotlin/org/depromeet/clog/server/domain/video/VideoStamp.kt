package org.depromeet.clog.server.domain.video

import java.time.LocalDateTime

data class VideoStamp(
    val id: Long? = null,
    val videoId: Long,
    val timeMs: Long,
    val createdAt: LocalDateTime? = null,
    val modifiedAt: LocalDateTime? = null,
)
