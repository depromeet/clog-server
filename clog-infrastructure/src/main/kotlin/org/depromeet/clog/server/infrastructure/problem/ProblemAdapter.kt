package org.depromeet.clog.server.infrastructure.problem

import org.depromeet.clog.server.domain.problem.ProblemCommand
import org.depromeet.clog.server.domain.problem.ProblemQuery
import org.depromeet.clog.server.domain.problem.ProblemRepository
import org.depromeet.clog.server.infrastructure.mappers.ProblemMapper
import org.springframework.stereotype.Component

@Component
class ProblemAdapter(
    private val problemMapper: ProblemMapper,
    private val problemJpaRepository: ProblemJpaRepository,
) : ProblemRepository {

    override fun save(problem: ProblemCommand): ProblemQuery {
        val entity = problemJpaRepository.save(problemMapper.toEntity(problem))
        return problemMapper.toDomain(entity)
    }

    override fun deleteById(problemId: Long) {
        return problemJpaRepository.deleteById(problemId)
    }
}
