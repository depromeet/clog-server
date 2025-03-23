package org.depromeet.clog.server.api.report.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "총 운동시간 응답 DTO")
data class TotalExerciseTime(

    @Schema(description = "총 운동 시간 (ms)", example = "120000")
    val totalExerciseTimeMs: Long,
)
