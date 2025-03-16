package org.depromeet.clog.server.api.video.presentation

import io.swagger.v3.oas.annotations.media.Schema
import org.depromeet.clog.server.api.attempt.presentation.dto.SaveStampRequest

@Schema(description = "영상 수정 요청 DTO")
data class VideoUpdateRequest(

    @Schema(description = "영상 파일 로컬 경로", example = "1")
    val localPath: String? = null,

    @Schema(description = "업로드된 썸네일 주소", example = "https://example.com/thumbnail.jpg")
    val thumbnailUrl: String? = null,

    @Schema(description = "영상 길이 (ms)", example = "12000")
    val durationMs: Long,

    @Schema(description = "영상 스탬프 목록")
    val stamps: List<SaveStampRequest>,
)
