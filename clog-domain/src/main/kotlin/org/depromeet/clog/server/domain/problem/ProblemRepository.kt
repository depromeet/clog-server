package org.depromeet.clog.server.domain.problem

interface ProblemRepository {

    fun save(problem: Problem): Problem

    fun findByIdOrNull(problemId: Long): Problem?
}
