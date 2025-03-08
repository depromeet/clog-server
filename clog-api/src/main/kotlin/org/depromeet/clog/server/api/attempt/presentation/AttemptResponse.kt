package org.depromeet.clog.server.api.attempt.presentation

import org.depromeet.clog.server.domain.attempt.Attempt
import org.depromeet.clog.server.domain.attempt.AttemptStatus

data class AttemptResponse(
    val status: AttemptStatus,
    val video: VideoResponse,
) {

    companion object {

        fun from(attempt: Attempt): AttemptResponse {
            return AttemptResponse(
                status = attempt.status,
                video = VideoResponse.from(attempt.video!!),
            )
        }
    }
}
