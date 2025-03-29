package org.depromeet.clog.server.api.attempt.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema

data class AttemptVideoResponse(
    @Schema(description = "비디오 id", example = "1")
    val id: Long,
    @Schema(description = "비디오 로컬 경로", example = "/videos/record_001.mp4")
    val localPath: String,
    @Schema(description = "비디오 썸네일 URL", example = "https://example.com/thumbnail.jpg")
    val thumbnailUrl: String? = null,
    @Schema(description = "비디오 길이 (ms)", example = "12000")
    val durationMs: Long
)
