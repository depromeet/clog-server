package org.depromeet.clog.server.api.report.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "가장 많이 방문한 암장 응답 DTO")
data class MostVisitedCrag(
    @Schema(description = "가장 많이 방문한 암장 이름", example = "더클라임 강남 지점")
    val mostVisitedCragName: String,
    @Schema(description = "가장 많이 방문한 암장 방문 횟수", example = "16")
    val mostVisitedCragVisitCount: Long
)
