package org.depromeet.clog.server.api.report.application

import io.github.oshai.kotlinlogging.KotlinLogging
import org.depromeet.clog.server.domain.report.DailyReportStatisticRepository
import org.depromeet.clog.server.domain.report.event.AttemptUpdatedEvent
import org.depromeet.clog.server.infrastructure.report.DailyReportStatisticCalculator
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class AttemptEventListener(
    private val dailyReportStatisticCalculator: DailyReportStatisticCalculator,
    private val dailyReportStatisticRepository: DailyReportStatisticRepository
) {

    private val logger = KotlinLogging.logger {}

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handleUpdatedEvent(event: AttemptUpdatedEvent) {
        logger.info { "AttemptUpdatedEvent 처리 중: userId=${event.userId}, attemptId=${event.attemptId}" }
        dailyReportStatisticCalculator.computeForUser(event.userId)?.let { statistic ->
            dailyReportStatisticRepository.save(statistic)
            logger.info { "userId=${event.userId}에 대한 통계가 업데이트되었습니다" }
        } ?: logger.warn { "userId=${event.userId}에 대해 계산된 통계가 없습니다" }
    }
}
