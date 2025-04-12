package org.depromeet.clog.server.api.crag.presentation.dto

import org.depromeet.clog.server.domain.crag.domain.Crag
import org.depromeet.clog.server.domain.crag.domain.color.Color

fun Crag.toGetMyCragInfoResponse(): CragResponse {
    return CragResponse(
        id = this.id!!,
        name = this.name,
        roadAddress = this.roadAddress
    )
}

fun Color.toGetMyColorResponse(): GetMyColorResponse {
    return GetMyColorResponse(
        gradeId = this.id!!,
        colorId = this.id!!,
        colorName = this.name,
        colorHex = this.hex
    )
}
