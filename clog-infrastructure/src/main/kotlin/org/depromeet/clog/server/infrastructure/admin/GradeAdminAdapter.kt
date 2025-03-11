package org.depromeet.clog.server.infrastructure.admin

import org.depromeet.clog.server.admin.domain.crag.GradeAdminRepository
import org.depromeet.clog.server.domain.crag.domain.Crag
import org.depromeet.clog.server.domain.crag.domain.Grade
import org.depromeet.clog.server.infrastructure.crag.CragEntity
import org.depromeet.clog.server.infrastructure.crag.GradeEntity
import org.depromeet.clog.server.infrastructure.crag.GradeJpaRepository
import org.springframework.stereotype.Component

@Component
class GradeAdminAdapter(
    private val gradeJpaRepository: GradeJpaRepository
) : GradeAdminRepository {

    override fun save(grade: Grade): Grade {
        return gradeJpaRepository.save(GradeEntity.fromDomain(grade)).toDomain()
    }

    override fun findByCrag(crag: Crag): List<Grade> {
        return gradeJpaRepository.findByCrag(CragEntity.fromDomain(crag)).map { it.toDomain() }
    }
}
