package org.depromeet.clog.server.infrastructure.report

import org.depromeet.clog.server.domain.report.ReportQuery
import org.depromeet.clog.server.infrastructure.user.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate

interface ReportJpaRepository : JpaRepository<UserEntity, Long> {

    @Query(
        """
        SELECT new org.depromeet.clog.server.domain.report.ReportQuery(
           COALESCE((SELECT COUNT(a1.id) 
                     FROM AttemptEntity a1 
                     JOIN a1.problem p1 
                     JOIN p1.story s1 
                     WHERE s1.userId = :userId AND s1.date >= :threeMonthsAgo), 0),
           
           COALESCE((SELECT SUM(v.durationMs) 
                     FROM AttemptEntity a2 
                     JOIN a2.problem p2 
                     JOIN p2.story s2 
                     JOIN a2.video v 
                     WHERE s2.userId = :userId), 0),
           
           COALESCE((SELECT COUNT(a3.id) 
                     FROM AttemptEntity a3 
                     JOIN a3.problem p3 
                     JOIN p3.story s3 
                     WHERE s3.userId = :userId), 0),
           
           COALESCE((SELECT COUNT(a4.id) 
                     FROM AttemptEntity a4 
                     JOIN a4.problem p4 
                     JOIN p4.story s4 
                     WHERE s4.userId = :userId AND a4.status = 'SUCCESS'), 0)
        )
        FROM UserEntity u
        WHERE u.id = :userId
        """
    )
    fun getReport(
        @Param("userId") userId: Long,
        @Param("threeMonthsAgo") threeMonthsAgo: LocalDate
    ): ReportQuery
}
