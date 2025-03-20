package org.depromeet.clog.server.domain.report

import org.depromeet.clog.server.domain.video.VideoQuery

data class DailyReportStatistic(
    val userId: Long,
    val mostAttemptedProblemCrag: String,
    val mostAttemptedProblemGrade: String,
    val mostAttemptedProblemAttemptCount: Long,
    val attemptVideos: List<VideoQuery>,
    val mostVisitedCragName: String,
    val mostVisitedCragVisitCount: Long,
)
