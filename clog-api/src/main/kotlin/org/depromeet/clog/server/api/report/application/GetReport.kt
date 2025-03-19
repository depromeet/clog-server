package org.depromeet.clog.server.api.report.application

import org.depromeet.clog.server.api.report.presentation.dto.ReportResponse
import org.depromeet.clog.server.domain.report.ReportRepository
import org.depromeet.clog.server.domain.user.domain.UserRepository
import org.depromeet.clog.server.domain.user.presentation.exception.UserNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class GetReport(
    private val reportRepository: ReportRepository,
    private val userRepository: UserRepository
) {
    @Transactional(readOnly = true)
    fun getMyReport(userId: Long): ReportResponse {
        val threeMonthsAgo = LocalDate.now().minusMonths(3)
        val reportQuery = reportRepository.getReport(userId, threeMonthsAgo)
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
}
