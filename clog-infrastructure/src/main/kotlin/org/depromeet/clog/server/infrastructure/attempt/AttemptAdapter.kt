package org.depromeet.clog.server.infrastructure.attempt

import org.depromeet.clog.server.domain.attempt.Attempt
import org.depromeet.clog.server.domain.attempt.AttemptRepository
import org.depromeet.clog.server.domain.attempt.dto.AttemptFilter
import org.depromeet.clog.server.domain.attempt.dto.AttemptFolderView
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class AttemptAdapter(
    private val attemptJpaRepository: AttemptJpaRepository,
) : AttemptRepository {

    override fun save(attempt: Attempt): Attempt {
        return attemptJpaRepository.save(AttemptEntity.fromDomain(attempt)).toDomain()
    }

    override fun findByIdOrNull(attemptId: Long): Attempt? {
        return attemptJpaRepository.findByIdOrNull(attemptId)?.toDomain()
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
