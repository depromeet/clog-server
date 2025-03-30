package org.depromeet.clog.server.api.report.application

import org.depromeet.clog.server.api.attempt.presentation.dto.AttemptVideoResponse
import org.depromeet.clog.server.api.report.presentation.dto.*
import org.depromeet.clog.server.domain.report.DailyReportStatistic
import org.depromeet.clog.server.domain.report.DailyReportStatisticRepository
import org.depromeet.clog.server.domain.report.ReportQuery
import org.depromeet.clog.server.domain.report.ReportRepository
import org.depromeet.clog.server.domain.user.domain.UserRepository
import org.depromeet.clog.server.domain.user.presentation.exception.UserNotFoundException
import org.depromeet.clog.server.domain.video.VideoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class GetReport(
    private val reportRepository: ReportRepository,
    private val userRepository: UserRepository,
    private val dailyReportStatisticRepository: DailyReportStatisticRepository,
    private val videoRepository: VideoRepository
) {

    @Transactional
    fun getMyReport(userId: Long): ReportResponse {
        val reportQuery = getReportQuery(userId)
        val statistic = dailyReportStatisticRepository.findByUserId(userId)

        return ReportResponse(
            userName = getUserName(userId),
            recentAttemptCount = reportQuery.recentAttemptCount,
            totalExerciseTime = buildTotalExerciseTime(reportQuery),
            totalAttemptCount = buildTotalAttemptCount(reportQuery),
            mostAttemptedProblem = buildMostAttemptedProblem(statistic),
            mostVisitedCrag = buildMostVisitedCrag(statistic),
        )
    }

    private fun buildTotalExerciseTime(reportQuery: ReportQuery): TotalExerciseTime {
        return TotalExerciseTime(totalExerciseTimeMs = reportQuery.totalExerciseTimeMs)
    }

    private fun buildMostAttemptedProblem(statistic: DailyReportStatistic?): MostAttemptedProblem? {
        return statistic?.let {
            val attemptVideos = videoRepository.findByProblemIdOrderByIdDesc(statistic.mostAttemptedProblemId)
                .map { video ->
                    AttemptVideoResponse(
                        id = video.id,
                        localPath = video.localPath,
                        thumbnailUrl = video.thumbnailUrl,
                        durationMs = video.durationMs
                    )
                }

            MostAttemptedProblem(
                mostAttemptedProblemCrag = statistic.mostAttemptedProblemCrag,
                mostAttemptedProblemGrade = statistic.mostAttemptedProblemGrade,
                mostAttemptedProblemAttemptCount = statistic.mostAttemptedProblemAttemptCount,
                attemptVideos = attemptVideos
            )
        }
    }

    private fun buildTotalAttemptCount(reportQuery: ReportQuery): TotalAttemptCount {
        val completionRate = if (reportQuery.totalAttemptCount > 0) {
            reportQuery.successAttemptCount.toDouble() / reportQuery.totalAttemptCount.toDouble() * 100
        } else {
            0.0
        }

        return TotalAttemptCount(
            successAttemptCount = reportQuery.successAttemptCount,
            totalAttemptCount = reportQuery.totalAttemptCount,
            completionRate = completionRate.toInt()
        )
    }

    private fun buildMostVisitedCrag(statistic: DailyReportStatistic?): MostVisitedCrag? {
        return statistic?.let {
            MostVisitedCrag(
                mostVisitedCragName = statistic.mostVisitedCragName,
                mostVisitedCragVisitCount = statistic.mostVisitedCragVisitCount
            )
        }
    }

    private fun getReportQuery(userId: Long): ReportQuery {
        val threeMonthsAgo = LocalDate.now().minusMonths(3)

        return reportRepository.getReport(userId, threeMonthsAgo)
    }

    private fun getUserName(userId: Long): String? {
        val user = userRepository.findByIdAndIsDeletedFalse(userId)
            ?: throw UserNotFoundException()

        return user.name
    }
}
