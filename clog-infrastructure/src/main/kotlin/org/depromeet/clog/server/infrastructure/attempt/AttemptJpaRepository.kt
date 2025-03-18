package org.depromeet.clog.server.infrastructure.attempt

import org.depromeet.clog.server.domain.attempt.AttemptStatus
import org.depromeet.clog.server.domain.attempt.dto.AttemptFolderView
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface AttemptJpaRepository : JpaRepository<AttemptEntity, Long> {

    @Query(
        """
        SELECT new org.depromeet.clog.server.domain.attempt.dto.AttemptFolderView(
            a.id,
            v.id,
            v.localPath,
            v.thumbnailUrl,
            v.durationMs,
            s.date,
            c.id,
            c.name,
            c.roadAddress,
            col.id,
            col.name,
            col.hex,
            a.status
        )
        FROM AttemptEntity a
        JOIN a.problem p
        JOIN p.story s
        JOIN a.video v
        LEFT JOIN p.grade g
        LEFT JOIN g.color col
        LEFT JOIN s.crag c
        WHERE s.userId = :userId
          AND (:attemptStatus IS NULL OR a.status = :attemptStatus)
          AND (:cragId IS NULL OR c.id = :cragId)
          AND (:gradeId IS NULL OR g.id = :gradeId)
        ORDER BY a.id DESC
    """
    )
    fun findAttemptsByUserAndFilter(
        @Param("userId") userId: Long,
        @Param("attemptStatus") attemptStatus: AttemptStatus?,
        @Param("cragId") cragId: Long?,
        @Param("gradeId") gradeId: Long?
    ): List<AttemptFolderView>

    fun findAllByProblemId(problemId: Long): List<AttemptEntity>
}
