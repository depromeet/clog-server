package org.depromeet.clog.server.api.attempt.presentation

import org.depromeet.clog.server.domain.attempt.Attempt
import org.depromeet.clog.server.domain.attempt.AttemptStatus

data class AttemptRequest(
    val status: AttemptStatus,

    val video: VideoRequest,
) {
    fun toDomain(
        problemId: Long,
        videoId: Long,
    ): Attempt {
        return Attempt(
            problemId = problemId,
            videoId = videoId,
            status = status,
        )
    }
}
