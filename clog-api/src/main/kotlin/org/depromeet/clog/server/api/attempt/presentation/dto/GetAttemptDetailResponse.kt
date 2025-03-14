package org.depromeet.clog.server.api.attempt.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.depromeet.clog.server.domain.attempt.AttemptStatus
import java.time.LocalDate

data class GetAttemptDetailResponse(
    @Schema(description = "비디오 id", example = "1")
    val videoId: Long,
    @Schema(description = "비디오 로컬 경로", example = "/videos/record_001.mp4")
    val videoLocalPath: String,
    @Schema(description = "비디오 썸네일 URL", example = "https://example.com/thumbnail.jpg")
    val videoThumbnailUrl: String,
    @Schema(description = "비디오 길이 (ms)", example = "12000")
    val videoDurationMs: Long,
    @Schema(description = "기록 날짜", example = "2025-03-13")
    val date: LocalDate,
    @Schema(description = "암장명", example = "강남 클라이밍 파크")
    val cragName: String?,
    @Schema(description = "난이도 색상 이름", example = "블루")
    val colorName: String?,
    @Schema(description = "난이도 색상 HEX", example = "#0000ff")
    val colorHEX: String?,
    @Schema(description = "완등 성공/실패 여부", example = "SUCCESS")
    val status: AttemptStatus
)
