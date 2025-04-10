package org.depromeet.clog.server.admin.api.crag.presentation.dto

import org.depromeet.clog.server.domain.crag.domain.grade.Grade

data class GradeResult(
    val colorName: String,
    val colorHex: String,
    val gradeOrder: Int?
) {

    companion object {
        fun from(grade: Grade): GradeResult {
            return GradeResult(
                colorName = grade.color.name,
                colorHex = grade.color.hex,
                gradeOrder = grade.order
            )
        }
    }
}
