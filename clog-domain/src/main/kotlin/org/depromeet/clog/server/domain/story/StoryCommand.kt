package org.depromeet.clog.server.domain.story

import java.time.LocalDate

data class StoryCommand(
    val id: Long? = null,
    val userId: Long? = null,
    val cragId: Long? = null,
    val memo: String? = null,
    val date: LocalDate,
)
