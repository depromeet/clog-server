package org.depromeet.clog.server.api.attempt.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.depromeet.clog.server.domain.video.VideoQuery

@Schema(description = "영상 응답 DTO")
data class VideoResponse(
    @Schema(description = "영상 ID", example = "1")
    val id: Long,

    @Schema(description = "영상 로컬 경로", example = "/storage/videos/1.mp4")
    val localPath: String,

    @Schema(description = "영상 썸네일 URL", example = "https://example.com/thumbnail.jpg")
    val thumbnailUrl: String,

    @Schema(description = "영상 길이 (ms)", example = "120000")
    val durationMs: Long,

    @Schema(description = "영상 스탬프")
    val stamps: List<VideoStampResponse> = emptyList(),
) {

    companion object {
        fun from(videoQuery: VideoQuery): VideoResponse {
            return VideoResponse(
                id = videoQuery.id!!,
                localPath = videoQuery.localPath,
                thumbnailUrl = videoQuery.thumbnailUrl,
                durationMs = videoQuery.durationMs,
                stamps = videoQuery.stamps.map(VideoStampResponse.Companion::from),
            )
        }
    }
}
