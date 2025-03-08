package org.depromeet.clog.server.api.attempt.presentation

import org.depromeet.clog.server.domain.video.VideoStamp

data class StampRequest(
    val timeMs: Long,
) {
    fun toDomain(videoId: Long): VideoStamp {
        return VideoStamp(
            videoId = videoId,
            timeMs = timeMs,
        )
    }
}
