package org.depromeet.clog.server.domain.crag.domain

import org.depromeet.clog.server.domain.crag.domain.grade.Grade

data class Crag(
    val id: Long? = null,
    val name: String,
    val roadAddress: String,
    val lotNumberAddress: String,
    val location: Location,
    val kakaoPlaceId: Long,
    val grades: List<Grade> = emptyList(),
) {

    fun validateRoadAddressNotBlank(): String {
        return if (this.roadAddress.isBlank()) lotNumberAddress else roadAddress
    }
}
