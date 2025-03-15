package org.depromeet.clog.server.api.crag.presentation.dto

import org.depromeet.clog.server.domain.crag.domain.Crag

fun Crag.toGetMyCragInfoResponse(): GetMyCragInfoResponse {
    return GetMyCragInfoResponse(
        id = this.id!!,
        name = this.name,
        roadAddress = this.roadAddress
    )
}
