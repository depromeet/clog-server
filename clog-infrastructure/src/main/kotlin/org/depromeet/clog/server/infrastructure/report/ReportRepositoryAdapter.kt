package org.depromeet.clog.server.infrastructure.report

import org.depromeet.clog.server.domain.report.ReportQuery
import org.depromeet.clog.server.domain.report.ReportRepository
import org.slf4j.LoggerFactory
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class ReportRepositoryAdapter(
    private val reportJpaRepository: ReportJpaRepository
) : ReportRepository {

    private val logger = LoggerFactory.getLogger(ReportRepositoryAdapter::class.java)

    override fun getReport(userId: Long, threeMonthsAgo: LocalDate): ReportQuery {
        return try {
            reportJpaRepository.getReport(userId, threeMonthsAgo)
        } catch (e: EmptyResultDataAccessException) {
            logger.warn("회원의 리포트 정보가 없습니다. {}: {}", userId, e.message, e)
            ReportQuery(0, 0, 0, 0)
        }
    }
}
