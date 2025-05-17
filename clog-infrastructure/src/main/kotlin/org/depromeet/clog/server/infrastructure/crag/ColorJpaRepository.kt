package org.depromeet.clog.server.infrastructure.crag

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface ColorJpaRepository : JpaRepository<ColorEntity, Long> {

    fun findByNameAndHex(name: String, hex: String): ColorEntity

    fun findByNameOrHex(name: String, hex: String): ColorEntity?

    @Query(
        """
        SELECT DISTINCT c
        FROM StoryEntity s
        JOIN s.problems p
        JOIN p.grade g
        JOIN g.color c
        WHERE s.userId = :userId
          AND (:cursor IS NULL OR c.id < :cursor)
        GROUP BY c.id
        ORDER BY MAX(g.id) DESC
        """
    )
    fun findDistinctColorsByUserIdWithCursor(
        @Param("userId") userId: Long,
        @Param("cursor") cursor: Long?,
        pageable: Pageable
    ): List<ColorEntity>
}
