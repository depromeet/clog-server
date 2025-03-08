package org.depromeet.clog.server.domain.problem

import org.depromeet.clog.server.domain.crag.domain.Crag
import org.depromeet.clog.server.domain.crag.domain.Grade

data class Problem(
    val id: Long,
    val crag: Crag? = null,
    val grad: Grade,
    val tags: List<String>,
    val createdAt: String,
    val updatedAt: String
)
