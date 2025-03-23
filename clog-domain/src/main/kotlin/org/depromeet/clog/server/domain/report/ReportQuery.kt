package org.depromeet.clog.server.domain.report

data class ReportQuery(
    val recentAttemptCount: Long,
    val totalExerciseTimeMs: Long,
    val totalAttemptCount: Long,
    val successAttemptCount: Long,
)
