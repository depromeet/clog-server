package org.depromeet.clog.server.infrastructure.attempt

import org.depromeet.clog.server.domain.attempt.AttemptStatus
import org.depromeet.clog.server.domain.attempt.dto.AttemptFolderView
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface AttemptJpaRepository : JpaRepository<AttemptEntity, Long> {

    fun findAllByProblemIdIn(problemIds: List<Long>): List<AttemptEntity>

    @Query(
        """
        SELECT new org.depromeet.clog.server.domain.attempt.dto.AttemptFolderView(
            a.id,            
            v.id,
            v.localPath,
            v.thumbnailUrl,
            v.durationMs,
            s.date,
            c.name,
            col.name,
            col.hex,
            a.status
        )
        FROM AttemptEntity a
        JOIN ProblemEntity p ON a.problemId = p.id
        JOIN StoryEntity s ON p.storyId = s.id
        JOIN VideoEntity v ON a.videoId = v.id
        LEFT JOIN GradeEntity g ON p.gradeId = g.id
        LEFT JOIN g.color col
        LEFT JOIN CragEntity c ON s.cragId = c.id
        WHERE s.userId = :userId
          AND (:attemptStatus IS NULL OR a.status = :attemptStatus)
          AND (:cragId IS NULL OR s.cragId = :cragId)
          AND (:gradeId IS NULL OR p.gradeId = :gradeId)
        ORDER BY a.id DESC
    """
    )
    fun findAttemptsByUserAndFilter(
        @Param("userId") userId: Long,
        @Param("attemptStatus") attemptStatus: AttemptStatus?,
        @Param("cragId") cragId: Long?,
        @Param("gradeId") gradeId: Long?
    ): List<AttemptFolderView>
}
