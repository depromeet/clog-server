package org.depromeet.clog.server.api.attempt.presentation

import org.depromeet.clog.server.domain.video.Video

data class VideoRequest(

    val localPath: String,

    val thumbnailUrl: String,

    val durationMs: Long,

    val stamps: List<StampRequest>,
) {
    fun toDomain(): Video {
        return Video(
            localPath = localPath,
            thumbnailUrl = thumbnailUrl,
            durationMs = durationMs,
        )
    }
}
