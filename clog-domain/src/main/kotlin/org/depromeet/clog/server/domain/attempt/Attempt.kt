package org.depromeet.clog.server.domain.attempt

import org.depromeet.clog.server.domain.video.Video

data class Attempt(
    val id: Long = 0L,
    val video: Video? = null,
    val status: AttemptStatus,
    val createdAt: String,
    val updatedAt: String,
)
