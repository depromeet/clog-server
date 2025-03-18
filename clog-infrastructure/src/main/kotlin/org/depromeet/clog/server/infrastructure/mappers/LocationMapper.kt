package org.depromeet.clog.server.infrastructure.mappers

import org.depromeet.clog.server.domain.crag.domain.Location
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Point
import org.locationtech.jts.geom.PrecisionModel
import org.springframework.stereotype.Component

@Component
class LocationMapper {

    private val geometryFactory = GeometryFactory(PrecisionModel(), 4326)

    fun toPoint(location: Location): Point {
        return geometryFactory.createPoint(Coordinate(location.longitude, location.latitude))
    }

    fun toLocation(point: Point): Location {
        return Location(
            longitude = point.coordinate.x,
            latitude = point.coordinate.y
        )
    }
}
