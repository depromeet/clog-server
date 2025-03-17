package org.depromeet.clog.server.infrastructure.mappers

import org.depromeet.clog.server.domain.attempt.AttemptCommand
import org.depromeet.clog.server.domain.attempt.AttemptQuery
import org.depromeet.clog.server.infrastructure.attempt.AttemptEntity
import org.depromeet.clog.server.infrastructure.problem.ProblemJpaRepository
import org.depromeet.clog.server.infrastructure.video.VideoJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class AttemptMapper(
    private val videoMapper: VideoMapper,
    private val videoJpaRepository: VideoJpaRepository,
    private val problemJpaRepository: ProblemJpaRepository,
) : DomainEntityMapper<AttemptQuery, AttemptCommand, AttemptEntity> {

    override fun toDomain(entity: AttemptEntity): AttemptQuery {
        return AttemptQuery(
            id = entity.id!!,
            video = videoMapper.toDomain(entity.video),
            status = entity.status,
        )
    }

    override fun toEntity(domain: AttemptCommand): AttemptEntity {
        val videoEntity = videoJpaRepository.findByIdOrNull(domain.videoId)
            ?: throw IllegalArgumentException("Video not found")

        val problemEntity = problemJpaRepository.findByIdOrNull(domain.problemId)
            ?: throw IllegalArgumentException("Problem not found")

        return AttemptEntity(
            id = domain.id,
            status = domain.status,
            video = videoEntity,
            problem = problemEntity,
        )
    }
}
