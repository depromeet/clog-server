package org.depromeet.clog.server.domain.report

import java.time.LocalDate

interface ReportRepository {
    fun getReport(userId: Long, threeMonthsAgo: LocalDate): ReportQuery
}
