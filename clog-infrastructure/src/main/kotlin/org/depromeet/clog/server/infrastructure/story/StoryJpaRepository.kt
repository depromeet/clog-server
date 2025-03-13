package org.depromeet.clog.server.infrastructure.story

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface StoryJpaRepository : JpaRepository<StoryEntity, Long>, KotlinJdslJpqlExecutor {

    fun findAllByUserIdAndDateGreaterThanEqualAndDateLessThanEqual(
        userId: Long,
        startDate: LocalDate,
        endDate: LocalDate,
    ): List<StoryEntity>

    fun findAllByUserId(userId: Long): List<StoryEntity>
}
