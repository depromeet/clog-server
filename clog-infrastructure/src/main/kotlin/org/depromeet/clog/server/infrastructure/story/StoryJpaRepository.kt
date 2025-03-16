package org.depromeet.clog.server.infrastructure.story

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.depromeet.clog.server.infrastructure.crag.CragEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate

interface StoryJpaRepository : JpaRepository<StoryEntity, Long>, KotlinJdslJpqlExecutor {

    fun findAllByUserIdAndDateGreaterThanEqualAndDateLessThanEqual(
        userId: Long,
        startDate: LocalDate,
        endDate: LocalDate,
    ): List<StoryEntity>

    @Query(
        """
        SELECT DISTINCT c
        FROM StoryEntity s
        JOIN CragEntity c ON s.cragId = c.id
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

    @Query(
        """
        SELECT DISTINCT g
        FROM StoryEntity s
        JOIN ProblemEntity p ON p.storyId = s.id
        JOIN GradeEntity g ON p.gradeId = g.id
        WHERE s.userId = :userId
          AND (:cursor IS NULL OR g.id < :cursor)
        ORDER BY g.id DESC
        """
    )
    fun findDistinctGradesByUserIdWithCursor(
        @Param("userId") userId: Long,
        @Param("cursor") cursor: Long?,
        pageable: Pageable
    ): List<org.depromeet.clog.server.infrastructure.crag.GradeEntity>
}
