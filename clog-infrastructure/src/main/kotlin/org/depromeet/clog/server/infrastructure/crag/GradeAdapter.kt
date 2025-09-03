package org.depromeet.clog.server.infrastructure.crag

import org.depromeet.clog.server.domain.crag.domain.grade.Grade
import org.depromeet.clog.server.domain.crag.domain.grade.GradeRepository
import org.depromeet.clog.server.infrastructure.mappers.GradeMapper
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class GradeAdapter(
    private val gradeMapper: GradeMapper,
    private val gradeJpaRepository: GradeJpaRepository,
) : GradeRepository {

    override fun findById(id: Long): Grade? {
        return gradeJpaRepository.findByIdOrNull(id)?.let {
            gradeMapper.toDomain(it)
        }
    }

    override fun findDistinctGradesByUserId(
        userId: Long,
        cursor: Long?,
        pageSize: Int
    ): List<Grade> {
        val pageable = PageRequest.of(0, pageSize + 1)
        return gradeJpaRepository.findDistinctGradesByUserIdWithCursor(userId, cursor, pageable)
            .map { gradeMapper.toDomain(it) }
    }

    override fun findGradesByCragId(cragId: Long): List<Grade> {
        val entities = gradeJpaRepository.findAll {
            select(
                entity(GradeEntity::class)
            ).from(
                entity(GradeEntity::class),
                fetchJoin(GradeEntity::crag),
                fetchJoin(GradeEntity::color)
            ).where(
                path(CragEntity::id).eq(cragId)
            ).orderBy(
                path(GradeEntity::order).asc()
            )
        }
            .filterNotNull()

        return entities.map { gradeMapper.toDomain(it) }
    }
}
