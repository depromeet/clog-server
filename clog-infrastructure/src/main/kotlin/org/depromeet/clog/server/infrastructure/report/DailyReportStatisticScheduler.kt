package org.depromeet.clog.server.infrastructure.report

import io.github.oshai.kotlinlogging.KotlinLogging
import org.depromeet.clog.server.domain.report.DailyReportStatisticRepository
import org.depromeet.clog.server.domain.user.domain.UserRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class DailyReportStatisticScheduler(
    private val userRepository: UserRepository,
    private val dailyReportStatisticRepository: DailyReportStatisticRepository,
    private val calculator: DailyReportStatisticCalculator
) {
    private val logger = KotlinLogging.logger {}

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
                    logger.info { "사용자 id ${user.id}의 통계가 업데이트되었습니다." }
                } else {
                    dailyReportStatisticRepository.save(computedStatistic)
                    logger.info { "사용자 id ${user.id}의 통계가 새로 저장되었습니다." }
                }
            } catch (e: Exception) {
                logger.error(e) { "사용자 id ${user.id}의 통계 계산 중 오류 발생: ${e.message}" }
            }
        }
        logger.info { "일일 통계 계산이 ${LocalDateTime.now()}에 완료되었습니다." }
    }
}
