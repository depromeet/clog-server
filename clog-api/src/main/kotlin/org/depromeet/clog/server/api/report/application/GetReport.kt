package org.depromeet.clog.server.api.report.application

import org.depromeet.clog.server.api.attempt.presentation.dto.AttemptVideoResponse
import org.depromeet.clog.server.api.report.presentation.dto.DetailedReportResponse
import org.depromeet.clog.server.api.report.presentation.dto.ReportResponse
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

    @Transactional(readOnly = true)
    fun getMyReport(userId: Long): ReportResponse {
        val threeMonthsAgo = LocalDate.now().minusMonths(3)
        val reportQuery: ReportQuery = reportRepository.getReport(userId, threeMonthsAgo)
        val user = userRepository.findByIdAndIsDeletedFalse(userId)
            ?: throw UserNotFoundException()
        val completionRate = if (reportQuery.totalAttemptCount > 0) {
            reportQuery.successAttemptCount.toDouble() / reportQuery.totalAttemptCount.toDouble() * 100
        } else {
            0.0
        }

        return ReportResponse(
            recentAttemptCount = reportQuery.recentAttemptCount,
            totalExerciseTimeMs = reportQuery.totalExerciseTimeMs,
            totalAttemptCount = reportQuery.totalAttemptCount,
            successAttemptCount = reportQuery.successAttemptCount,
            completionRate = completionRate,
            userNickname = user.name
        )
    }

    @Transactional
    fun getReportStatistic(userId: Long): DetailedReportResponse {
        userRepository.findByIdAndIsDeletedFalse(userId)
            ?: throw UserNotFoundException()

        var statistic = dailyReportStatisticRepository.findByUserId(userId)
        if (statistic == null) {
            statistic = calculator.computeForUser(userId)
            statistic = dailyReportStatisticRepository.save(statistic)
        }

        val videoResponses = statistic.attemptVideos.map { video ->
            AttemptVideoResponse(
                id = video.id,
                localPath = video.localPath,
                thumbnailUrl = video.thumbnailUrl,
                durationMs = video.durationMs
            )
        }

        return DetailedReportResponse(
            mostAttemptedProblemCrag = statistic.mostAttemptedProblemCrag,
            mostAttemptedProblemGrade = statistic.mostAttemptedProblemGrade,
            mostAttemptedProblemAttemptCount = statistic.mostAttemptedProblemAttemptCount,
            attemptVideos = videoResponses,
            mostVisitedCragName = statistic.mostVisitedCragName,
            mostVisitedCragVisitCount = statistic.mostVisitedCragVisitCount
        )
    }
}
