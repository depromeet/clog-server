package org.depromeet.clog.server.infrastructure.admin

import org.depromeet.clog.server.domain.admin.GradeAdminRepository
import org.depromeet.clog.server.domain.crag.domain.Crag
import org.depromeet.clog.server.domain.crag.domain.grade.Grade
import org.depromeet.clog.server.infrastructure.crag.GradeJpaRepository
import org.depromeet.clog.server.infrastructure.mappers.CragMapper
import org.depromeet.clog.server.infrastructure.mappers.GradeMapper
import org.springframework.stereotype.Component

@Component
class GradeAdminAdapter(
    private val cragMapper: CragMapper,
    private val gradeMapper: GradeMapper,
    private val gradeJpaRepository: GradeJpaRepository
) : GradeAdminRepository {

    override fun save(grade: Grade): Grade {
        val entity = gradeJpaRepository.save(gradeMapper.toEntity(grade))
        return gradeMapper.toDomain(entity)
    }

    override fun findByCrag(crag: Crag): List<Grade> {
        return gradeJpaRepository.findByCrag(cragMapper.toEntity(crag)).map { gradeMapper.toDomain(it) }
    }
}
