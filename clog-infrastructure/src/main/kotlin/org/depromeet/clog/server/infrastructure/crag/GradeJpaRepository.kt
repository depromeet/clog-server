package org.depromeet.clog.server.infrastructure.crag

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface GradeJpaRepository : JpaRepository<GradeEntity, Long> {

    @Query(
        """
        SELECT DISTINCT g
        FROM StoryEntity s
        JOIN s.problems p
        JOIN p.grade g
        WHERE s.userId = :userId
          AND (:cursor IS NULL OR g.id < :cursor)
        ORDER BY g.id DESC
        """
    )
    fun findDistinctGradesByUserIdWithCursor(
        @Param("userId") userId: Long,
        @Param("cursor") cursor: Long?,
        pageable: Pageable
    ): List<GradeEntity>
}
