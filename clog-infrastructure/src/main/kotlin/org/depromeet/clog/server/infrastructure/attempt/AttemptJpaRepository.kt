package org.depromeet.clog.server.infrastructure.attempt

import org.springframework.data.jpa.repository.JpaRepository

interface AttemptJpaRepository : JpaRepository<AttemptEntity, Long> {

    fun findAllByProblemIdIn(problemIds: List<Long>): List<AttemptEntity>
}
