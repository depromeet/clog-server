package org.depromeet.clog.server.api.report.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "사용자 리포트 응답 DTO")
data class ReportResponse(
    @Schema(description = "사용자 닉네임", example = "김클로그")
    val userName: String,

    @Schema(description = "최근 3개월 내 시도 횟수", example = "10")
    val recentAttemptCount: Long,

    @Schema(description = "총 운동시간")
    val totalExerciseTime: TotalExerciseTime,

    @Schema(description = "총 시도 횟수")
    val totalAttemptCount: TotalAttemptCount,

    @Schema(description = "가장 많이 시도한 도전")
    val mostAttemptedProblem: MostAttemptedProblem?,

    @Schema(description = "가장 많이 방문한 암장")
    val mostVisitedCrag: MostVisitedCrag?,
)
