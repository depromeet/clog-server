package org.depromeet.clog.server.infrastructure.story

import org.depromeet.clog.server.domain.story.Story
import org.depromeet.clog.server.domain.story.StoryRepository
import org.depromeet.clog.server.infrastructure.attempt.AttemptJpaRepository
import org.depromeet.clog.server.infrastructure.problem.ProblemJpaRepository
import org.depromeet.clog.server.infrastructure.user.UserEntity
import org.depromeet.clog.server.infrastructure.video.VideoJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class StoryAdapter(
    private val storyJpaRepository: StoryJpaRepository,
    private val problemJpaRepository: ProblemJpaRepository,
    private val attemptJpaRepository: AttemptJpaRepository,
    private val videoJpaRepository: VideoJpaRepository,
) : StoryRepository {

    override fun save(story: Story): Story {
        return storyJpaRepository.save(StoryEntity.from(story)).toDomain()
    }

    override fun findByIdOrNull(storyId: Long): Story? {
        return storyJpaRepository.findByIdOrNull(storyId)?.toDomain()
    }

    override fun findAggregate(storyId: Long): Story? {
        val story = storyJpaRepository.findByIdOrNull(storyId)
            ?: return null

        val problems = problemJpaRepository.findAllByStoryId(story.id!!)
        val attempts = attemptJpaRepository.findAllByProblemIdIn(problems.map { it.id!! })
        val videos = videoJpaRepository.findAllById(attempts.map { it.videoId })

        problems.map {
            it.toDomain(
                attempts = attempts.filter { attempt -> attempt.problemId == it.id }
                    .map { attempt ->
                        attempt.toDomain(
                            video = videos.find { video -> video.id == attempt.videoId }?.toDomain()
                        )
                    }
            )
        }.let {
            return story.toDomain(it)
        }
    }

    override fun findAllByUserIdAndDateBetween(userId: Long, startDate: LocalDate, endDate: LocalDate): List<Story> {
        val storyIds = storyJpaRepository.findAll {
            select(
                path(StoryEntity::id)
            ).from(
                entity(UserEntity::class),
                join(StoryEntity::class).on(
                    path(StoryEntity::userId).eq(path(UserEntity::id))
                ),
            ).where(
                and(
                    path(StoryEntity::userId).eq(userId),
                    path(StoryEntity::date).between(startDate.minusDays(1), endDate.plusDays(1))
                )
            )
        }.filterNotNull()

        return storyIds.mapNotNull {
            this.findAggregate(it)
        }
    }

    override fun deleteById(storyId: Long) {
        storyJpaRepository.deleteById(storyId)
    }
}
