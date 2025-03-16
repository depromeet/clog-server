package org.depromeet.clog.server.infrastructure.mappers

import org.depromeet.clog.server.domain.crag.domain.Crag
import org.depromeet.clog.server.infrastructure.crag.CoordinateEntity
import org.depromeet.clog.server.infrastructure.crag.CragEntity
import org.springframework.stereotype.Component

@Component
class CragMapper(
    private val gradeMapper: GradeMapper,
) {

    fun toDomain(entity: CragEntity): Crag {
        return Crag(
            id = entity.id!!,
            name = entity.name,
            roadAddress = entity.roadAddress,
            coordinate = entity.coordinate.toDomain(),
            kakaoPlaceId = entity.kakaoPlaceId,
            grades = entity.grades.map {
                gradeMapper.toDomain(it)
            }
        )
    }

    fun toEntity(domain: Crag): CragEntity {
        return CragEntity(
            id = domain.id,
            name = domain.name,
            roadAddress = domain.roadAddress,
            coordinate = CoordinateEntity.fromDomain(domain.coordinate),
            kakaoPlaceId = domain.kakaoPlaceId,
            grades = domain.grades.map {
                gradeMapper.toEntity(it)
            }
        )
    }
}
