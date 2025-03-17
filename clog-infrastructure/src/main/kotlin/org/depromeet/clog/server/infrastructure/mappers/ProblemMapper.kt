package org.depromeet.clog.server.infrastructure.mappers

import org.depromeet.clog.server.domain.problem.ProblemCommand
import org.depromeet.clog.server.domain.problem.ProblemQuery
import org.depromeet.clog.server.infrastructure.attempt.AttemptJpaRepository
import org.depromeet.clog.server.infrastructure.crag.GradeJpaRepository
import org.depromeet.clog.server.infrastructure.problem.ProblemEntity
import org.depromeet.clog.server.infrastructure.story.StoryJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class ProblemMapper(
    private val gradeMapper: GradeMapper,
    private val attemptMapper: AttemptMapper,
    private val storyJpaRepository: StoryJpaRepository,
    private val gradeJpaRepository: GradeJpaRepository,
    private val attemptJpaRepository: AttemptJpaRepository,
) : DomainEntityMapper<ProblemQuery, ProblemCommand, ProblemEntity> {

    override fun toDomain(entity: ProblemEntity): ProblemQuery {
        return ProblemQuery(
            id = entity.id!!,
            grade = entity.grade?.let { gradeMapper.toDomain(it) },
            attempts = entity.attempts.map { attemptMapper.toDomain(it) },
        )
    }

    override fun toEntity(domain: ProblemCommand): ProblemEntity {
        val storyEntity = storyJpaRepository.findByIdOrNull(domain.storyId)
            ?: throw IllegalArgumentException("Story not found")

        val gradeEntity = gradeJpaRepository.findByIdOrNull(domain.gradeId)
            ?: throw IllegalArgumentException("Grade not found")

        return ProblemEntity(
            id = domain.id,
            story = storyEntity,
            grade = gradeEntity,
            attempts = domain.id?.let { attemptJpaRepository.findAllByProblemId(it) } ?: emptyList(),
        )
    }
}
