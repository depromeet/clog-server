package org.depromeet.clog.server.domain.story

import java.time.LocalDate
import java.time.LocalDateTime

data class Story(
    val id: Long? = null,
    val userId: Long,
    val cragId: Long? = null,
    val memo: String? = null,
    val date: LocalDate,
    val createdAt: LocalDateTime? = null,
    val updatedAt: LocalDateTime? = null,
)
