package org.depromeet.clog.server.domain.problem

import org.depromeet.clog.server.domain.attempt.Attempt

data class Problem(
    val id: Long? = null,
    val storyId: Long,
    val gradeId: Long? = null,

    val attempts: List<Attempt> = emptyList(),
)
