package org.depromeet.clog.server.domain.video

import java.time.LocalDateTime

data class Video(
    val id: Long? = null,
    val localPath: String,
    val thumbnailUrl: String,
    val durationMs: Long,
    val stamps: List<VideoStamp> = emptyList(),
    val createdAt: LocalDateTime? = null,
    val modifiedAt: LocalDateTime? = null,
)
