package org.depromeet.clog.server.api.attempt.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.depromeet.clog.server.api.crag.presentation.dto.CragResponse
import org.depromeet.clog.server.api.grade.presentation.dto.ColorResponse
import org.depromeet.clog.server.domain.attempt.AttemptStatus
import java.time.LocalDate

data class GetAttemptDetailResponse(
    @Schema(description = "시도 id", example = "1")
    val attemptId: Long,
    @Schema(description = "비디오 정보")
    val video: AttemptVideoResponse,
    @Schema(description = "기록 날짜", example = "2025-03-13")
    val date: LocalDate,
    @Schema(description = "암장 관련 정보")
    val crag: CragResponse? = null,
    @Schema(description = "난이도 색상 관련 정보")
    val color: ColorResponse? = null,
    @Schema(description = "완등 성공/실패 여부", example = "SUCCESS")
    val status: AttemptStatus
)
