package org.depromeet.clog.server.infrastructure.report

import org.depromeet.clog.server.domain.report.DailyReportStatistic
import org.depromeet.clog.server.domain.report.DailyReportStatisticRepository
import org.depromeet.clog.server.infrastructure.mappers.DailyReportStatisticMapper
import org.springframework.stereotype.Component

@Component
class DailyReportStatisticAdapter(
    private val dailyReportStatisticJpaRepository: DailyReportStatisticJpaRepository,
    private val dailyReportStatisticMapper: DailyReportStatisticMapper
) : DailyReportStatisticRepository {

    override fun findByUserId(userId: Long): DailyReportStatistic? {
        val entity = dailyReportStatisticJpaRepository.findById(userId).orElse(null) ?: return null
        return dailyReportStatisticMapper.toDomain(entity)
    }

    override fun save(statistic: DailyReportStatistic): DailyReportStatistic {
        val entity = dailyReportStatisticMapper.toEntity(statistic)
        val saved = dailyReportStatisticJpaRepository.save(entity)
        return findByUserId(saved.userId)!!
    }
}
