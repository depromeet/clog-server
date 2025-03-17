package org.depromeet.clog.server.api.grade.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.depromeet.clog.server.domain.crag.domain.color.Color

@Schema(description = "난이도의 색상 정보입니다.")
data class ColorResponse(
    @Schema(description = "색상 ID", example = "1")
    val id: Long,

    @Schema(description = "색상 이름", example = "노랑")
    val name: String,

    @Schema(description = "색상 HEX 코드", example = "#FF0000")
    val hex: String,
) {

    companion object {
        fun from(color: Color): ColorResponse {
            return ColorResponse(
                id = color.id ?: 0L,
                name = color.name,
                hex = color.hex,
            )
        }
    }
}
