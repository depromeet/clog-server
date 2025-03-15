package org.depromeet.clog.server.infrastructure.story

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.depromeet.clog.server.domain.crag.dto.GetMyCragInfo
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
        SELECT DISTINCT new org.depromeet.clog.server.domain.crag.dto.GetMyCragInfo(
            c.id,
            c.name
        )
        FROM StoryEntity s
        JOIN CragEntity c ON s.cragId = c.id
        WHERE s.userId = :userId
    """
    )
    fun findDistinctCragsByUserId(@Param("userId") userId: Long): List<GetMyCragInfo>
}
