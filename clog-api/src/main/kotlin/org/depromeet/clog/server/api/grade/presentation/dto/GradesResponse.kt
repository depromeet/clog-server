package org.depromeet.clog.server.api.grade.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "암장에 포함된 난이도 응답")
data class GradesResponse(
    @Schema(description = "암장에 포함된 난이도")
    val grades: List<GetGradeResponse>
)
