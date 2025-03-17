package org.depromeet.clog.server.api.grade.presentation.dto

import org.depromeet.clog.server.domain.crag.domain.grade.Grade

fun Grade.toGetMyGradeInfoResponse(): GetMyGradeInfoResponse {
    return GetMyGradeInfoResponse(
        gradeId = this.id!!,
        colorName = this.color.name,
        colorHex = this.color.hex
    )
}
