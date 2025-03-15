package org.depromeet.clog.server.api.grade.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "내가 기록한 난이도 정보 응답 DTO")
data class GetMyGradeInfoResponse(
    @Schema(description = "난이도 id", example = "1")
    val gradeId: Long,
    @Schema(description = "난이도 색상 이름", example = "블루")
    val colorName: String,
    @Schema(description = "난이도 색상 HEX", example = "#0000ff")
    val colorHex: String
)
