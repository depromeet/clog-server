package org.depromeet.clog.server.domain.attempt

data class AttemptCommand(
    val id: Long? = null,
    val problemId: Long,
    val videoId: Long,
    val status: AttemptStatus,
)
