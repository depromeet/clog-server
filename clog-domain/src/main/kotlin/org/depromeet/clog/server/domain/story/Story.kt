package org.depromeet.clog.server.domain.story

import org.depromeet.clog.server.domain.crag.domain.Crag
import java.time.Instant
import java.time.LocalDate

data class Story(
    val id: Long = 0L,
    val crag: Crag,
    val memo: String,
    val date: LocalDate,
    val createdAt: Instant,
    val updatedAt: Instant,
)
