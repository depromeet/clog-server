package org.depromeet.clog.server.domain.crag.domain

data class Grade(
    val id: Long? = null,
    val color: Color,
    val crag: Crag,
    val order: Int? = null,
)
