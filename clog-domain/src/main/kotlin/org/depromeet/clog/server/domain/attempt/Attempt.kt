package org.depromeet.clog.server.domain.attempt

import java.time.Instant

data class Attempt(
    val id: Long? = null,
    val problemId: Long,
    val videoId: Long,
    val status: AttemptStatus,
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
)
