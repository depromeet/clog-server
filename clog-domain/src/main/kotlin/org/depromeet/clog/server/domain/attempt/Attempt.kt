package org.depromeet.clog.server.domain.attempt

import org.depromeet.clog.server.domain.video.Video

data class Attempt(
    val id: Long? = null,
    val problemId: Long,
    val videoId: Long,
    val status: AttemptStatus,

    val video: Video? = null,
)
