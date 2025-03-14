package org.depromeet.clog.server.api.attempt.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "폴더(나의 클라이밍 기록) 응답")
data class GetAttemptResponse(
    @Schema(description = "시도한 문제 상세정보")
    val attempts: List<GetAttemptDetailResponse>
)
