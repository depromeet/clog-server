package org.depromeet.clog.server.infrastructure.report

import jakarta.persistence.*
import org.depromeet.clog.server.infrastructure.common.BaseEntity
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
    var mostAttemptedProblemCrag: String? = null,

    @Column(nullable = false)
    var mostAttemptedProblemGrade: String? = null,

    @Column(nullable = false)
    var mostAttemptedProblemAttemptCount: Long,

    @Column(name = "most_attempted_problem_id", nullable = false)
    var mostAttemptedProblemId: Long,

    @Column(nullable = false)
    var mostVisitedCragName: String,

    @Column(nullable = false)
    var mostVisitedCragVisitCount: Long
) : BaseEntity()
