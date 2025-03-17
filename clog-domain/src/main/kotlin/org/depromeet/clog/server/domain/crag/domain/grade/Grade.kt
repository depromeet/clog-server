package org.depromeet.clog.server.domain.crag.domain.grade

import org.depromeet.clog.server.domain.crag.domain.color.Color

data class Grade(
    val id: Long? = null,
    val cragId: Long,
    val color: Color,
    val order: Int? = null,
)
