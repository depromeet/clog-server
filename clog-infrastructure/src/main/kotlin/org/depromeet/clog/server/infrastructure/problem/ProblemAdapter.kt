package org.depromeet.clog.server.infrastructure.problem

import org.depromeet.clog.server.domain.problem.Problem
import org.depromeet.clog.server.domain.problem.ProblemRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class ProblemAdapter(
    private val problemJpaRepository: ProblemJpaRepository,
) : ProblemRepository {

    override fun save(problem: Problem): Problem {
        return problemJpaRepository.save(ProblemEntity.fromDomain(problem)).toDomain()
    }

    override fun findByIdOrNull(problemId: Long): Problem? {
        return problemJpaRepository.findByIdOrNull(problemId)?.toDomain()
    }
}
