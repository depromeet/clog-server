package org.depromeet.clog.server.domain.crag.domain

data class Crag(
    val id: Long? = null,
    val name: String,
    val roadAddress: String,
    val coordinate: Coordinate,
    val kakaoPlaceId: Long,
    val grades: List<Grade> = emptyList(),
)
