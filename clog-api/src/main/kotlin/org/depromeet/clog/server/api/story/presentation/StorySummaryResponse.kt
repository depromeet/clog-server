package org.depromeet.clog.server.api.story.presentation

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "스토리 요약 응답 DTO")
data class StorySummaryResponse(
    @Schema(description = "스토리 ID", example = "1")
    val id: Long,

    @Schema(description = "암장 이름", example = "강남 클라이밍 파크")
    val cragName: String? = null,

    @Schema(description = "해당 기록의 총 운동 시간", example = "60000")
    val totalDurationMs: Long,

    @Schema(description = "해당 기록의 총 시도 횟수", example = "10")
    val totalAttemptsCount: Int,

    @Schema(description = "해당 기록의 총 성공 횟수", example = "5")
    val totalSuccessCount: Int,

    @Schema(description = "해당 기록의 총 실패 횟수", example = "5")
    val totalFailCount: Int,

    @Schema(description = "메모", example = "오늘은 너무 힘들엇다.")
    val memo: String? = null,

    @Schema(description = "문제 목록")
    val problems: List<Problem>,
) {

    @Schema(name = "StorySummaryResponse.Problem", description = "문제 정보")
    data class Problem(
        @Schema(description = "문제 ID", example = "1")
        val id: Long,

        @Schema(description = "시도 횟수", example = "10")
        val attemptCount: Int,

        @Schema(description = "색상값", example = "#FF0000")
        val colorHex: String? = null,
    )
}
