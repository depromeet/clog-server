package org.depromeet.clog.server.api.crag.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "내가 기록한 암장정보 응답 DTO")
data class GetMyCragInfoResponse(
    @Schema(description = "암장 id", example = "1")
    val id: Long,
    @Schema(description = "암장명", example = "강남 클라이밍 파크")
    val name: String
)
