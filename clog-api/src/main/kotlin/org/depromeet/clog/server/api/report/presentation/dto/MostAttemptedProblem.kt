package org.depromeet.clog.server.api.report.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.depromeet.clog.server.api.attempt.presentation.dto.AttemptVideoResponse

@Schema(description = "가장 많이 시도한 도전 응답 DTO")
data class MostAttemptedProblem(
    @Schema(description = "가장 많이 도전한 문제의 암장 이름", example = "클라이밍 파크 강남점")
    val mostAttemptedProblemCrag: String?,

    @Schema(description = "가장 많이 도전한 문제의 난이도", example = "노랑")
    val mostAttemptedProblemGrade: String?,

    @Schema(description = "가장 많이 도전한 문제의 도전 횟수", example = "10")
    val mostAttemptedProblemAttemptCount: Long,

    @Schema(description = "해당 문제의 시도 영상 목록")
    val attemptVideos: List<AttemptVideoResponse>,
)
