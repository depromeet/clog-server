package org.depromeet.clog.server.domain.report

data class DailyReportStatistic(
    val userId: Long,
    val mostAttemptedProblemCrag: String?,
    val mostAttemptedProblemGrade: String?,
    val mostAttemptedProblemAttemptCount: Long,
    val mostAttemptedProblemId: Long,
    val mostVisitedCragName: String,
    val mostVisitedCragVisitCount: Long,
)
