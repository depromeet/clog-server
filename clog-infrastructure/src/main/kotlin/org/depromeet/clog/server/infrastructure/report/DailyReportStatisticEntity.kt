package org.depromeet.clog.server.infrastructure.report

import jakarta.persistence.*
import org.depromeet.clog.server.infrastructure.common.BaseEntity

@Entity
@Table(name = "daily_report_statistic")
class DailyReportStatisticEntity(
    @Id
    val userId: Long,

    @Column(nullable = false)
    var mostAttemptedProblemCrag: String,

    @Column(nullable = false)
    var mostAttemptedProblemGrade: String,

    @Column(nullable = false)
    var mostAttemptedProblemAttemptCount: Long,

    @Column(columnDefinition = "TEXT")
    var attemptVideosJson: String,

    @Column(nullable = false)
    var mostVisitedCragName: String,

    @Column(nullable = false)
    var mostVisitedCragVisitCount: Long
) : BaseEntity()
