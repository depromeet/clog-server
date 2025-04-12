package org.depromeet.clog.server.api.crag.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "내가 기록한 난이도 정보 응답 DTO")
data class GetMyColorResponse(
    @Schema(description = "색상 id", example = "1")
    val colorId: Long,

    @Schema(description = "색상 이름", example = "블루")
    val colorName: String,

    @Schema(description = "색상 HEX", example = "#0000ff")
    val colorHex: String,

    @Deprecated("난이도 id가 아닌 색상 id를 활용해주세요.")
    @Schema(description = "난이도 id", example = "1")
    val gradeId: Long,
)
