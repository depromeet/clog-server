package org.depromeet.clog.server.infrastructure.report

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(
    name = "daily_report_statistic",
    uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "stat_date"])]
)
class DailyReportStatisticEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "user_id", nullable = false)
    var userId: Long,

    @Column(name = "stat_date", nullable = false)
    var statDate: LocalDate,

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
)
