package org.depromeet.clog.server.infrastructure.crag

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import org.depromeet.clog.server.domain.crag.domain.Coordinate
import org.hibernate.annotations.Comment

@Embeddable
class CoordinateEntity(
    @Comment("경도 좌표")
    @Column(nullable = false)
    val longitude: Double,

    @Comment("위도 좌표")
    @Column(nullable = false)
    val latitude: Double
) {

    fun toDomain(): Coordinate = Coordinate(
        longitude = this.longitude,
        latitude = this.latitude
    )

    companion object {
        fun fromDomain(coordinate: Coordinate): CoordinateEntity = CoordinateEntity(
            longitude = coordinate.longitude,
            latitude = coordinate.latitude
        )
    }
}
