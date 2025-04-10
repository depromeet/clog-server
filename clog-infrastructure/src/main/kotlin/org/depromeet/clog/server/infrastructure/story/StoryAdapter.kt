package org.depromeet.clog.server.infrastructure.story

import org.depromeet.clog.server.domain.story.StoryCommand
import org.depromeet.clog.server.domain.story.StoryQuery
import org.depromeet.clog.server.domain.story.StoryRepository
import org.depromeet.clog.server.domain.story.StoryStatus
import org.depromeet.clog.server.infrastructure.attempt.AttemptEntity
import org.depromeet.clog.server.infrastructure.mappers.StoryMapper
import org.depromeet.clog.server.infrastructure.problem.ProblemEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class StoryAdapter(
    private val storyMapper: StoryMapper,
    private val storyJpaRepository: StoryJpaRepository,
) : StoryRepository {

    override fun save(story: StoryCommand): StoryQuery {
        return storyMapper.toEntity(story)
            .let { storyJpaRepository.save(it) }
            .let { storyMapper.toDomain(it) }
    }

    override fun findByIdOrNull(storyId: Long): StoryQuery? {
        return storyJpaRepository.findByIdOrNull(storyId)
            ?.let { storyMapper.toDomain(it) }
    }

    override fun findAllByUserIdAndDateBetween(
        userId: Long,
        startDate: LocalDate,
        endDate: LocalDate,
    ): List<StoryQuery> {
        return storyJpaRepository.findAllByUserIdAndDateBetweenAndStatus(
            userId = userId,
            startDate = startDate.minusDays(1),
            endDate = endDate.plusDays(1),
            status = StoryStatus.DONE,
        ).map { storyMapper.toDomain(it) }
    }

    override fun findByAttemptId(attemptId: Long): StoryQuery? {
        val entity = storyJpaRepository.findAll {
            val subquery = select<Long>(
                path(StoryEntity::id)
            ).from(
                entity(StoryEntity::class),
                join(StoryEntity::problems),
                join(ProblemEntity::attempts)
            ).where(
                path(AttemptEntity::id).eq(attemptId)
            ).asSubquery()

            select(
                entity(StoryEntity::class)
            ).from(
                entity(StoryEntity::class),
                fetchJoin(StoryEntity::problems),
                join(ProblemEntity::attempts)
            ).where(
                subquery.eq(path(StoryEntity::id)),
            )
        }.filterNotNull().firstOrNull()

        return entity?.let { storyMapper.toDomain(it) }
    }

    override fun deleteById(storyId: Long) {
        storyJpaRepository.deleteById(storyId)
    }
}
