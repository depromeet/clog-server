package org.depromeet.clog.server.api.report.application

import org.depromeet.clog.server.domain.report.DailyReportStatisticRepository
import org.depromeet.clog.server.domain.report.event.AttemptCreatedEvent
import org.depromeet.clog.server.infrastructure.report.DailyReportStatisticCalculator
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class AttemptCreatedEventListener(
    private val dailyReportStatisticCalculator: DailyReportStatisticCalculator,
    private val dailyReportStatisticRepository: DailyReportStatisticRepository
) {

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handleAttemptCreatedEvent(event: AttemptCreatedEvent) {
        dailyReportStatisticCalculator.computeForUser(event.userId)?.let { statistic ->
            dailyReportStatisticRepository.save(statistic)
        }
    }
}
