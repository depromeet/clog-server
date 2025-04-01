package org.depromeet.clog.server.domain.report.event

data class AttemptCreatedEvent(
    val userId: Long,
    val attemptId: Long
)
