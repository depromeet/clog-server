package org.depromeet.clog.server.infrastructure.mappers

import org.depromeet.clog.server.domain.crag.domain.grade.Grade
import org.depromeet.clog.server.infrastructure.crag.CragJpaRepository
import org.depromeet.clog.server.infrastructure.crag.GradeEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class GradeMapper(
    private val colorMapper: ColorMapper,
    private val cragJpaRepository: CragJpaRepository,
) : DomainEntityMapper<Grade, Grade, GradeEntity> {

    override fun toDomain(entity: GradeEntity): Grade {
        return Grade(
            id = entity.id!!,
            color = colorMapper.toDomain(entity.color),
            order = entity.order,
            cragId = entity.crag.id!!
        )
    }

    override fun toEntity(domain: Grade): GradeEntity {
        val cragEntity = cragJpaRepository.findByIdOrNull(domain.cragId)
            ?: throw IllegalArgumentException("Crag not found with id: ${domain.cragId}")

        return GradeEntity(
            id = domain.id,
            color = colorMapper.toEntity(domain.color),
            order = domain.order,
            crag = cragEntity
        )
    }
}
