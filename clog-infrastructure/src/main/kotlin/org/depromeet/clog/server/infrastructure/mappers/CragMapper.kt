package org.depromeet.clog.server.infrastructure.mappers

import org.depromeet.clog.server.domain.crag.domain.Crag
import org.depromeet.clog.server.domain.crag.domain.Location
import org.depromeet.clog.server.infrastructure.crag.CragEntity
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.PrecisionModel
import org.springframework.stereotype.Component

@Component
class CragMapper(
    private val gradeMapper: GradeMapper,
) : DomainEntityMapper<Crag, Crag, CragEntity> {

    override fun toDomain(entity: CragEntity): Crag {
        return Crag(
            id = entity.id!!,
            name = entity.name,
            roadAddress = entity.roadAddress,
            location = Location(
                longitude = entity.location.coordinate.x,
                latitude = entity.location.coordinate.y
            ),
            kakaoPlaceId = entity.kakaoPlaceId,
            grades = entity.grades.map {
                gradeMapper.toDomain(it)
            }
        )
    }

    override fun toEntity(domain: Crag): CragEntity {
        val geometryFactory = GeometryFactory(PrecisionModel(), 4326)
        val point = geometryFactory.createPoint(
            Coordinate(domain.location.longitude, domain.location.latitude)
        )

        return CragEntity(
            id = domain.id,
            name = domain.name,
            roadAddress = domain.roadAddress,
            location = point,
            kakaoPlaceId = domain.kakaoPlaceId,
            grades = domain.grades.map {
                gradeMapper.toEntity(it)
            }
        )
    }
}
