package org.depromeet.clog.server.domain.problem

import java.time.Instant

data class Problem(
    val id: Long? = null,
    val storyId: Long,
    val gradeId: Long? = null,
    val createdAt: Instant? = null,
    val updatedAt: Instant? = null,
)
