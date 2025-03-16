package org.depromeet.clog.server.domain.attempt

import org.depromeet.clog.server.domain.video.VideoQuery

data class AttemptQuery(
    val id: Long,
    val video: VideoQuery,
    val status: AttemptStatus,
) {
    val isSuccess: Boolean
        get() = status == AttemptStatus.SUCCESS

    val isFail: Boolean
        get() = status == AttemptStatus.FAILURE
}
