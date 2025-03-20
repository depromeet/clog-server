package org.depromeet.clog.server.infrastructure.report

import org.depromeet.clog.server.domain.report.DailyReportStatisticRepository
import org.depromeet.clog.server.domain.user.domain.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class DailyReportStatisticScheduler(
    private val userRepository: UserRepository,
    private val dailyReportStatisticRepository: DailyReportStatisticRepository,
    private val calculator: DailyReportStatisticCalculator
) {
    private val logger = LoggerFactory.getLogger(DailyReportStatisticScheduler::class.java)

    @Scheduled(cron = "0 0 3 * * ?")
    fun calculateDailyStatistics() {
        val activeUsers = userRepository.findAllActiveUsers()
        activeUsers.forEach { user ->
            try {
                val computedStatistic = calculator.computeForUser(user.id!!)
                val existingStatistic = dailyReportStatisticRepository.findByUserId(user.id!!)
                if (existingStatistic != null) {
                    val updatedStatistic = existingStatistic.copy(
                        mostAttemptedProblemCrag = computedStatistic.mostAttemptedProblemCrag,
                        mostAttemptedProblemGrade = computedStatistic.mostAttemptedProblemGrade,
                        mostAttemptedProblemAttemptCount = computedStatistic.mostAttemptedProblemAttemptCount,
                        attemptVideos = computedStatistic.attemptVideos,
                        mostVisitedCragName = computedStatistic.mostVisitedCragName,
                        mostVisitedCragVisitCount = computedStatistic.mostVisitedCragVisitCount
                    )
                    dailyReportStatisticRepository.save(updatedStatistic)
                } else {
                    dailyReportStatisticRepository.save(computedStatistic)
                }
            } catch (e: Exception) {
                logger.error("사용자 id ${user.id}의 통계 계산 중 오류 발생: ${e.message}", e)
            }
        }
        logger.info("일일 통계 계산이 ${LocalDateTime.now()}에 완료되었습니다.")
    }
}
