package org.depromeet.clog.server.infrastructure.story

import org.depromeet.clog.server.domain.story.Story
import org.depromeet.clog.server.domain.story.StoryRepository
import org.depromeet.clog.server.infrastructure.attempt.AttemptJpaRepository
import org.depromeet.clog.server.infrastructure.problem.ProblemJpaRepository
import org.depromeet.clog.server.infrastructure.video.VideoJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

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
}
