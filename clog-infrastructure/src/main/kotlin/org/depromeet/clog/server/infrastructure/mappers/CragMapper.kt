package org.depromeet.clog.server.infrastructure.mappers

import org.depromeet.clog.server.domain.crag.domain.Crag
import org.depromeet.clog.server.infrastructure.crag.CragEntity
import org.springframework.stereotype.Component

@Component
class CragMapper(
    private val gradeMapper: GradeMapper,
    private val locationMapper: LocationMapper
) : DomainEntityMapper<Crag, Crag, CragEntity> {

    override fun toDomain(entity: CragEntity): Crag {
        val location = locationMapper.toLocation(entity.location)

        return Crag(
            id = entity.id!!,
            name = entity.name,
            roadAddress = entity.roadAddress,
            location = location,
            kakaoPlaceId = entity.kakaoPlaceId,
            grades = entity.grades.map {
                gradeMapper.toDomain(it)
            }
        )
    }

    override fun toEntity(domain: Crag): CragEntity {
        val location = locationMapper.toPoint(domain.location)

        return CragEntity(
            id = domain.id,
            name = domain.name,
            roadAddress = domain.roadAddress,
            location = location,
            kakaoPlaceId = domain.kakaoPlaceId,
            grades = domain.grades.map {
                gradeMapper.toEntity(it)
            }
        )
    }
}
