package org.depromeet.clog.server.domain.story

import org.depromeet.clog.server.domain.problem.Problem
import java.time.LocalDate

data class Story(
    val id: Long? = null,
    val userId: Long,
    val cragId: Long? = null,
    val memo: String? = null,
    val date: LocalDate,

    val problems: List<Problem> = emptyList(),
)
