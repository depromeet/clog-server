package org.depromeet.clog.server.api.attempt.presentation.dto

import org.depromeet.clog.server.domain.attempt.AttemptStatus

data class AttemptFilter(
    val attemptStatus: AttemptStatus? = null,
    val cragId: Long? = null,
    val gradeId: Long? = null
)
