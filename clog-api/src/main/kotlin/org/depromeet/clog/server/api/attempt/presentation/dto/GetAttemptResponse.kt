package org.depromeet.clog.server.api.attempt.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.depromeet.clog.server.api.crag.presentation.dto.CragResponse
import org.depromeet.clog.server.api.grade.presentation.dto.ColorResponse

@Schema(description = "시도 상세 조회 응답")
data class GetAttemptResponse(
    @Schema(description = "기록 ID", example = "1")
    val storyId: Long,

    @Schema(description = "문제 ID", example = "1")
    val problemId: Long,

    @Schema(description = "해당 문제 난이도의 색상 정보")
    val color: ColorResponse? = null,

    @Schema(description = "해당 시도의 암장 관련 정보")
    val crag: CragResponse? = null,

    @Schema(description = "해당 시도의 상세 정보")
    val attempt: AttemptDetailResponse
)
