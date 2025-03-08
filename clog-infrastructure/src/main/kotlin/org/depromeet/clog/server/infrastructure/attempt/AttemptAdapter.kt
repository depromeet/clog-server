package org.depromeet.clog.server.infrastructure.attempt

import org.depromeet.clog.server.domain.attempt.Attempt
import org.depromeet.clog.server.domain.attempt.AttemptRepository
import org.springframework.stereotype.Component

@Component
class AttemptAdapter(
    private val attemptJpaRepository: AttemptJpaRepository,
) : AttemptRepository {

    override fun save(attempt: Attempt): Attempt {
        return attemptJpaRepository.save(AttemptEntity.fromDomain(attempt)).toDomain()
    }
}
