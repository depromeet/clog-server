package org.depromeet.clog.server.infrastructure.mappers

import org.depromeet.clog.server.domain.story.StoryCommand
import org.depromeet.clog.server.domain.story.StoryQuery
import org.depromeet.clog.server.infrastructure.crag.CragJpaRepository
import org.depromeet.clog.server.infrastructure.problem.ProblemJpaRepository
import org.depromeet.clog.server.infrastructure.story.StoryEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class StoryMapper(
    private val problemMapper: ProblemMapper,
    private val cragMapper: CragMapper,
    private val cragJpaRepository: CragJpaRepository,
    private val problemJpaRepository: ProblemJpaRepository,
) : DomainEntityMapper<StoryQuery, StoryCommand, StoryEntity> {

    override fun toDomain(entity: StoryEntity): StoryQuery {
        return StoryQuery(
            id = entity.id!!,
            userId = entity.userId,
            status = entity.status,
            memo = entity.memo,
            date = entity.date,
            problems = entity.problems.map { problemMapper.toDomain(it) }.toMutableList(),
            crag = entity.crag?.let { cragMapper.toDomain(it) },
        )
    }

    override fun toEntity(domain: StoryCommand): StoryEntity {
        val cragEntity = domain.cragId?.let { cragJpaRepository.findByIdOrNull(domain.cragId) }

        val problemEntities = domain.id?.let { problemJpaRepository.findAllByStoryId(it) }
            ?: emptyList()

        return StoryEntity(
            id = domain.id,
            crag = cragEntity,
            problems = problemEntities,
            userId = domain.userId,
            memo = domain.memo,
            date = domain.date,
            status = domain.status,
        )
    }
}
