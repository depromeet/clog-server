package org.depromeet.clog.server.infrastructure.mappers

import org.depromeet.clog.server.domain.report.DailyReportStatistic
import org.depromeet.clog.server.infrastructure.report.DailyReportStatisticEntity
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class DailyReportStatisticMapper {

    fun toDomain(entity: DailyReportStatisticEntity): DailyReportStatistic {
        return DailyReportStatistic(
            userId = entity.userId,
            mostAttemptedProblemCrag = entity.mostAttemptedProblemCrag,
            mostAttemptedProblemGrade = entity.mostAttemptedProblemGrade,
            mostAttemptedProblemAttemptCount = entity.mostAttemptedProblemAttemptCount,
            mostAttemptedProblemId = entity.mostAttemptedProblemId,
            mostVisitedCragName = entity.mostVisitedCragName,
            mostVisitedCragVisitCount = entity.mostVisitedCragVisitCount
        )
    }

    fun toEntity(domain: DailyReportStatistic): DailyReportStatisticEntity {
        return DailyReportStatisticEntity(
            id = null,
            userId = domain.userId,
            statDate = LocalDate.now(),
            mostAttemptedProblemCrag = domain.mostAttemptedProblemCrag,
            mostAttemptedProblemGrade = domain.mostAttemptedProblemGrade,
            mostAttemptedProblemAttemptCount = domain.mostAttemptedProblemAttemptCount,
            mostAttemptedProblemId = domain.mostAttemptedProblemId,
            mostVisitedCragName = domain.mostVisitedCragName,
            mostVisitedCragVisitCount = domain.mostVisitedCragVisitCount
        )
    }
}
