package org.depromeet.clog.server.domain.report

interface DailyReportStatisticRepository {
    fun findByUserId(userId: Long): DailyReportStatistic?
    fun save(statistic: DailyReportStatistic): DailyReportStatistic
}
