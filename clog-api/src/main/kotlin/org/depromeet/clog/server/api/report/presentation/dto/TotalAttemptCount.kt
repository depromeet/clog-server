package org.depromeet.clog.server.api.report.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "총 시도 횟수 응답 DTO")
data class TotalAttemptCount(
    @Schema(description = "성공한 시도 횟수", example = "15")
    val successAttemptCount: Long,
    @Schema(description = "전체 시도 횟수", example = "20")
    val totalAttemptCount: Long,
    @Schema(description = "완등률 (%)", example = "75")
    val completionRate: Int
)
