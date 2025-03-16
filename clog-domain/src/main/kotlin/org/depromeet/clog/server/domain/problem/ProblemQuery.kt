package org.depromeet.clog.server.domain.problem

import org.depromeet.clog.server.domain.attempt.AttemptQuery
import org.depromeet.clog.server.domain.crag.domain.grade.Grade

data class ProblemQuery(
    val id: Long,
    val grade: Grade? = null,
    val attempts: List<AttemptQuery> = emptyList(),
)
