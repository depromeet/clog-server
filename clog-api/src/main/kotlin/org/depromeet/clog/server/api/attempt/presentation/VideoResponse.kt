package org.depromeet.clog.server.api.attempt.presentation

import org.depromeet.clog.server.domain.video.Video
import org.depromeet.clog.server.domain.video.VideoStamp

data class VideoResponse(
    val id: Long,
    val localPath: String,
    val thumbnailUrl: String,
    val durationMs: Long,
    val stamps: List<VideoStamp> = emptyList(),
) {

    companion object {
        fun from(video: Video): VideoResponse {
            return VideoResponse(
                id = video.id!!,
                localPath = video.localPath,
                thumbnailUrl = video.thumbnailUrl,
                durationMs = video.durationMs,
                stamps = video.stamps,
            )
        }
    }
}
