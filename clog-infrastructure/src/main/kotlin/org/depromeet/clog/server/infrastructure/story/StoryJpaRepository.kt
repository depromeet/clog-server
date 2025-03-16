package org.depromeet.clog.server.infrastructure.story

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface StoryJpaRepository : JpaRepository<StoryEntity, Long>, KotlinJdslJpqlExecutor {

    fun findAllByUserIdAndDateBetween(userId: Long, startDate: LocalDate, endDate: LocalDate): List<StoryEntity>
}
