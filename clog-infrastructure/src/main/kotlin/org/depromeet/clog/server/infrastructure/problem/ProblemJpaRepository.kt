package org.depromeet.clog.server.infrastructure.problem

import org.springframework.data.jpa.repository.JpaRepository

interface ProblemJpaRepository : JpaRepository<ProblemEntity, Long> {

    fun findAllByStoryId(problemId: Long): List<ProblemEntity>
}
