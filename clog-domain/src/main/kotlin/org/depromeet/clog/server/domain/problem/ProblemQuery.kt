package org.depromeet.clog.server.domain.problem

import org.depromeet.clog.server.domain.attempt.AttemptQuery
import org.depromeet.clog.server.domain.crag.domain.grade.Grade
import org.depromeet.clog.server.global.utils.Constants.DEFAULT_GRADE_COLOR

data class ProblemQuery(
    val id: Long,
    val grade: Grade? = null,
    val attempts: List<AttemptQuery> = emptyList(),
) {

    val colorHexWithDefault: String
        get() = grade?.color?.hex ?: DEFAULT_GRADE_COLOR
}
