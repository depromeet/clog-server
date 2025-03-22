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
        val entity = dailyReportStatisticJpaRepository.findByUserId(userId)
        return entity?.let { dailyReportStatisticMapper.toDomain(it) }
    }

    override fun save(statistic: DailyReportStatistic): DailyReportStatistic {
        val existingEntity = dailyReportStatisticJpaRepository.findByUserId(statistic.userId)
        return if (existingEntity != null) {
            existingEntity.mostAttemptedProblemCrag = statistic.mostAttemptedProblemCrag
            existingEntity.mostAttemptedProblemGrade = statistic.mostAttemptedProblemGrade
            existingEntity.mostAttemptedProblemAttemptCount =
                statistic.mostAttemptedProblemAttemptCount
            existingEntity.attemptVideosJson =
                dailyReportStatisticMapper.writeAttemptVideos(statistic.attemptVideos)
            existingEntity.mostVisitedCragName = statistic.mostVisitedCragName
            existingEntity.mostVisitedCragVisitCount = statistic.mostVisitedCragVisitCount
            existingEntity.statDate = java.time.LocalDate.now()
            val saved = dailyReportStatisticJpaRepository.save(existingEntity)
            dailyReportStatisticMapper.toDomain(saved)
        } else {
            val entity = dailyReportStatisticMapper.toEntity(statistic)
            val saved = dailyReportStatisticJpaRepository.save(entity)
            dailyReportStatisticMapper.toDomain(saved)
        }
    }
}
