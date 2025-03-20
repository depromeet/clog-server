package org.depromeet.clog.server.api.grade.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.depromeet.clog.server.domain.crag.domain.grade.Grade

@Schema(description = "난이도 응답 DTO")
data class GetGradeResponse(
    @Schema(description = "난이도 ID", example = "1")
    val gradeId: Long,

    @Schema(description = "난이도", example = "1")
    val order: Int,

    @Schema(description = "난이도 색상 정보")
    val color: ColorResponse
) {

    companion object {
        fun from(grade: Grade): GetGradeResponse {
            return GetGradeResponse(
                gradeId = grade.id!!,
                order = grade.order!!,
                color = ColorResponse(
                    id = grade.color.id!!,
                    name = grade.color.name,
                    hex = grade.color.hex
                )
            )
        }
    }
}
