package org.depromeet.clog.server.api.calendar.application

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDate

@Schema(
    description = "캘린더 메인 화면에 보여져야 하는 정보를 총합합니다.",
)
data class CalendarResponse(
    @Schema(
        description = "해당 월에 클라이밍을 한 날짜 수",
        example = "5",
    )
    val numOfClimbDays: Int,

    @Schema(
        description = "해당 월에 클라이밍을 한 총 시간 (ms)",
        example = "3600000",
    )
    val totalDurationMs: Long,

    @Schema(
        description = "해당 월에 클라이밍을 한 총 시도 개수",
        example = "10",
    )
    val totalAttemptCount: Int,

    @Schema(description = "시도 성공 횟수", example = "7")
    val successAttemptCount: Int,

    @Schema(description = "시도 실패 횟수", example = "3")
    val failAttemptCount: Int,

    @Schema(
        description = "해당 월에 클라이밍을 한 날짜별 스토리 리스트",
    )
    val days: List<Day>,
) {

    data class Day(
        @Schema(
            description = "해당 날짜",
            example = "2023-10-01",
        )
        val date: LocalDate,

        @Schema(
            description = "해당 날짜에 클라이밍을 한 스토리의 썸네일 URL",
            example = "https://example.com/thumbnail.jpg",
        )
        val thumbnailUrl: String? = null,

        @Schema(
            description = "해당 날짜에 클라이밍을 한 스토리 리스트",
        )
        val stories: List<Story>,
    )

    data class Story(
        @Schema(
            description = "스토리 ID",
            example = "12345",
        )
        val id: Long,

        @Schema(
            description = "해당 스토리의 총 클라이밍 시간 (ms)",
            example = "3600000",
        )
        val totalDurationMs: Long,

        @Schema(
            description = "해당 스토리의 크랙 이름",
            example = "크랙 이름",
        )
        val cragName: String?,

        @Schema(
            description = "해당 스토리의 문제 리스트",
        )
        val problems: List<Problem>,
    )

    data class Problem(
        @Schema(
            description = "문제의 색상 HEX 코드",
            example = "#FF5733",
        )
        val colorHex: String?,

        @Schema(
            description = "해당 색상의 문제 개수",
            example = "3",
        )
        val count: Int,
    )
}
