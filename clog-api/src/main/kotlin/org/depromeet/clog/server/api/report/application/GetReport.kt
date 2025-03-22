package org.depromeet.clog.server.api.report.application

import org.depromeet.clog.server.api.attempt.presentation.dto.AttemptVideoResponse
import org.depromeet.clog.server.api.report.presentation.dto.*
import org.depromeet.clog.server.domain.report.DailyReportStatisticRepository
import org.depromeet.clog.server.domain.report.ReportQuery
import org.depromeet.clog.server.domain.report.ReportRepository
import org.depromeet.clog.server.domain.user.domain.UserRepository
import org.depromeet.clog.server.domain.user.presentation.exception.UserNotFoundException
import org.depromeet.clog.server.infrastructure.report.DailyReportStatisticCalculator
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class GetReport(
    private val reportRepository: ReportRepository,
    private val userRepository: UserRepository,
    private val dailyReportStatisticRepository: DailyReportStatisticRepository,
    private val calculator: DailyReportStatisticCalculator
) {

    @Transactional
    fun getMyReport(userId: Long): ReportResponse {
        val threeMonthsAgo = LocalDate.now().minusMonths(3)
        val reportQuery: ReportQuery = reportRepository.getReport(userId, threeMonthsAgo)
        val user = userRepository.findByIdAndIsDeletedFalse(userId)
            ?: throw UserNotFoundException("사용자를 찾을 수 없습니다.")
        val totalExerciseTime = TotalExerciseTime(
            totalExerciseTimeMs = reportQuery.totalExerciseTimeMs
        )
        val completionRate = if (reportQuery.totalAttemptCount > 0) {
            reportQuery.successAttemptCount.toDouble() / reportQuery.totalAttemptCount.toDouble() * 100
        } else {
            0.0
        }
        val totalAttemptCount = TotalAttemptCount(
            successAttemptCount = reportQuery.successAttemptCount,
            totalAttemptCount = reportQuery.totalAttemptCount,
            completionRate = completionRate
        )

        var statistic = dailyReportStatisticRepository.findByUserId(userId)
        if (statistic == null) {
            statistic = calculator.computeForUser(userId)
            statistic = dailyReportStatisticRepository.save(statistic)
        }

        val attemptVideos = statistic.attemptVideos.map { video ->
            AttemptVideoResponse(
                id = video.id,
                localPath = video.localPath,
                thumbnailUrl = video.thumbnailUrl,
                durationMs = video.durationMs
            )
        }

        return ReportResponse(
            userName = user.name,
            recentAttemptCount = reportQuery.recentAttemptCount,
            totalExerciseTime = totalExerciseTime,
            totalAttemptCount = totalAttemptCount,
            mostAttemptedProblem = MostAttemptedProblem(
                mostAttemptedProblemCrag = statistic.mostAttemptedProblemCrag,
                mostAttemptedProblemGrade = statistic.mostAttemptedProblemGrade,
                mostAttemptedProblemAttemptCount = statistic.mostAttemptedProblemAttemptCount,
                attemptVideos = attemptVideos
            ),
            mostVisitedCrag = MostVisitedCrag(
                mostVisitedCragName = statistic.mostVisitedCragName,
                mostVisitedCragVisitCount = statistic.mostVisitedCragVisitCount
            )
        )
    }
}
