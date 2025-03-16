package org.depromeet.clog.server.api.calendar.application

import io.swagger.v3.oas.annotations.media.Schema
import org.depromeet.clog.server.domain.story.StoryQuery
import java.time.LocalDate

@Schema(
    description = "캘린더 메인 화면에 보여져야 하는 정보를 총합합니다.",
)
data class CalendarResponse(
    @Schema(
        description = "월 별 정보에 대한 요약입니다.",
    )
    val summary: Summary,

    @Schema(
        description = "해당 월에 클라이밍을 한 날짜별 스토리 리스트",
    )
    val days: List<Day>,
) {

    @Schema(name = "CalendarResponse.Summary", description = "월 별 정보에 대한 요약입니다.")
    data class Summary(
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
    ) {
        companion object {
            fun from(stories: List<StoryQuery>): Summary {
                val storiesGroupedByDate = stories.groupBy { it.date }

                return Summary(
                    numOfClimbDays = storiesGroupedByDate.size,
                    totalDurationMs = stories.sumOf { it.totalDurationMs },
                    totalAttemptCount = stories.sumOf { it.problems.sumOf { problem -> problem.attempts.size } },
                    successAttemptCount = stories.sumOf {
                        it.problems.sumOf { problem -> problem.attempts.count { attempt -> attempt.isSuccess } }
                    },
                    failAttemptCount = stories.sumOf {
                        it.problems.sumOf { problem -> problem.attempts.count { attempt -> !attempt.isFail } }
                    },
                )
            }
        }
    }

    @Schema(name = "CalendarResponse.Day", description = "해당 월에 클라이밍을 한 날짜별 기록 리스트")
    data class Day(
        @Schema(
            description = "해당 날짜",
            example = "2023-10-01",
        )
        val date: LocalDate,

        @Schema(
            description = "해당 날짜에 클라이밍을 한 기록의 대표 썸네일 URL",
            example = "https://example.com/thumbnail.jpg",
        )
        val thumbnailUrl: String? = null,

        @Schema(
            description = "각 날짜에 표시될 기록 목록",
        )
        val stories: List<StoryListItem>,
    )

    @Schema(name = "CalendarResponse.StoryListItem", description = "각 날짜에 표시될 기록 목록의 아이템")
    data class StoryListItem(
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

    @Schema(name = "CalendarResponse.Problem", description = "해당 기록의 문제 리스트")
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
