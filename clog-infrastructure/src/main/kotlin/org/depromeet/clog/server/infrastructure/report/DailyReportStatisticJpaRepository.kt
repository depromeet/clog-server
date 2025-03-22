package org.depromeet.clog.server.infrastructure.report

import org.springframework.data.jpa.repository.JpaRepository

interface DailyReportStatisticJpaRepository : JpaRepository<DailyReportStatisticEntity, Long> {
    fun findByUserId(userId: Long): DailyReportStatisticEntity?
}
