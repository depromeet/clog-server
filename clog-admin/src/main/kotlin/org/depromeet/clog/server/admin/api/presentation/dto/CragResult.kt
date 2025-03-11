package org.depromeet.clog.server.admin.api.presentation.dto

import org.depromeet.clog.server.domain.crag.domain.Crag

data class CragResult(
    val id: Long,
    val name: String,
    val roadAddress: String,
    val longitude: Double,
    val latitude: Double,
    val kakaoPlaceId: Long
) {

    companion object {
        fun from(crag: Crag): CragResult {
            return CragResult(
                id = crag.id!!,
                name = crag.name,
                roadAddress = crag.roadAddress,
                longitude = crag.coordinate.longitude,
                latitude = crag.coordinate.latitude,
                kakaoPlaceId = crag.kakaoPlaceId
            )
        }
    }
}
