package org.depromeet.clog.server.domain.problem

interface ProblemRepository {

    fun save(problem: ProblemCommand): ProblemQuery

    fun deleteById(problemId: Long)
}
