package org.depromeet.clog.server.infrastructure.crag

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface CragJpaRepository : JpaRepository<CragEntity, Long> {
    fun existsByKakaoPlaceId(kakaoPlaceId: Long): Boolean

    @Query(
        """
        SELECT DISTINCT c
        FROM StoryEntity s
        JOIN s.crag c
        WHERE s.userId = :userId
          AND (:cursor IS NULL OR c.id < :cursor)
        ORDER BY c.id DESC
        """
    )
    fun findDistinctCragsByUserIdWithCursor(
        @Param("userId") userId: Long,
        @Param("cursor") cursor: Long?,
        pageable: Pageable
    ): List<CragEntity>
}
