package org.depromeet.clog.server.domain.problem

interface ProblemRepository {

    fun save(problem: Problem): Problem
}
