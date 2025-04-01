package org.depromeet.clog.server.domain.report.event

data class AttemptDeletedEvent(
    val userId: Long,
    val attemptId: Long
)
