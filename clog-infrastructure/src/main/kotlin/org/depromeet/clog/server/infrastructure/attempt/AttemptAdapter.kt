package org.depromeet.clog.server.infrastructure.attempt

import org.depromeet.clog.server.domain.attempt.AttemptCommand
import org.depromeet.clog.server.domain.attempt.AttemptQuery
import org.depromeet.clog.server.domain.attempt.AttemptRepository
import org.depromeet.clog.server.domain.attempt.dto.AttemptFilter
import org.depromeet.clog.server.domain.attempt.dto.AttemptFolderView
import org.depromeet.clog.server.infrastructure.mappers.AttemptMapper
import org.springframework.stereotype.Component

@Component
class AttemptAdapter(
    private val attemptMapper: AttemptMapper,
    private val attemptJpaRepository: AttemptJpaRepository,
) : AttemptRepository {

    override fun save(attempt: AttemptCommand): AttemptQuery {
        val entity = attemptJpaRepository.save(attemptMapper.toEntity(attempt))
        return attemptMapper.toDomain(entity)
    }

    override fun deleteById(attemptId: Long) {
        attemptJpaRepository.deleteById(attemptId)
    }

    override fun findAttemptsByUserAndFilter(
        userId: Long,
        filter: AttemptFilter
    ): List<AttemptFolderView> {
        return attemptJpaRepository.findAttemptsByUserAndFilter(
            userId,
            filter.attemptStatus,
            filter.cragId,
            filter.gradeId
        )
    }
}
