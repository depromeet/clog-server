package org.depromeet.clog.server.infrastructure.crag

import org.depromeet.clog.server.domain.crag.domain.Grade
import org.depromeet.clog.server.domain.crag.domain.GradeRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class GradeAdapter(
    private val gradeJpaRepository: GradeJpaRepository,
) : GradeRepository {
    override fun findById(id: Long): Grade? {
        return gradeJpaRepository.findByIdOrNull(id)
            ?.toDomain()
    }
}
