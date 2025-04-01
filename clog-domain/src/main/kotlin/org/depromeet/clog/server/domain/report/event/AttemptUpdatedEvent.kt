package org.depromeet.clog.server.domain.report.event

data class AttemptUpdatedEvent(
    val userId: Long,
    val attemptId: Long
)
