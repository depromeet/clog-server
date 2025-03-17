package org.depromeet.clog.server.domain.problem

data class ProblemCommand(
    val id: Long? = null,
    val storyId: Long,
    val gradeId: Long? = null,
)
