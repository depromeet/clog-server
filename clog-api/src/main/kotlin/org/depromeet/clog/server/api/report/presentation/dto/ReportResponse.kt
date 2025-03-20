package org.depromeet.clog.server.api.report.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "사용자 리포트 응답 DTO")
data class ReportResponse(
    @Schema(description = "최근 3개월 내 시도 횟수", example = "10")
    val recentAttemptCount: Long,
    @Schema(description = "총 운동 시간 (ms)", example = "120000")
    val totalExerciseTimeMs: Long,
    @Schema(description = "전체 시도 횟수", example = "20")
    val totalAttemptCount: Long,
    @Schema(description = "성공한 시도 횟수", example = "15")
    val successAttemptCount: Long,
    @Schema(description = "완등률 (%)", example = "75.0")
    val completionRate: Double,
    @Schema(description = "사용자 닉네임", example = "김클로그")
    val userNickname: String
)
