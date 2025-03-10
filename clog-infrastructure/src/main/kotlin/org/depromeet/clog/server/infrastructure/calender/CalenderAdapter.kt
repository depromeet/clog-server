package org.depromeet.clog.server.infrastructure.calender

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import org.depromeet.clog.server.domain.calender.Calender
import org.depromeet.clog.server.domain.calender.CalenderRepository
import org.depromeet.clog.server.domain.video.Video
import org.depromeet.clog.server.infrastructure.attempt.AttemptEntity
import org.depromeet.clog.server.infrastructure.problem.ProblemEntity
import org.depromeet.clog.server.infrastructure.story.StoryEntity
import org.depromeet.clog.server.infrastructure.story.StoryJpaRepository
import org.depromeet.clog.server.infrastructure.user.UserEntity
import org.depromeet.clog.server.infrastructure.video.VideoEntity
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class CalenderAdapter(
    private val storyJpaRepository: StoryJpaRepository,
) : CalenderRepository {

    override fun getCalender(userId: Long, year: Int, month: Int): Calender {
        val storiesOfMonth = storyJpaRepository.findAllByUserIdAndDateGreaterThanEqualAndDateLessThanEqual(
            userId = userId,
            startDate = LocalDate.of(year, month, 1),
            endDate = LocalDate.of(year, month, 1).plusMonths(1),
        )

        val numOfClimbDays = storiesOfMonth.groupBy { it.date }.size
        val totalClimbTime = this.calculateTotalClimbTime(userId, year, month)

        storiesOfMonth.let { stories ->
            val days = stories.groupBy { it.date }.map { (date, stories) ->
                Calender.Day(
                    date = date,
                    storyIds = stories.map { it.id!! },
                    thumbnailUrl = this.findRandomVideoBy(stories.first().id!!)?.thumbnailUrl
                )
            }
            return Calender(
                numOfClimbDays = numOfClimbDays,
                totalClimbTime = totalClimbTime,
                days = days,
            )
        }
    }

    override fun calculateTotalClimbTime(userId: Long, year: Int, month: Int): Int {
        return storyJpaRepository.findAll {
            jpql {
                select(
                    sum(
                        path(VideoEntity::durationMs)
                    )
                ).from(
                    entity(UserEntity::class),
                    join(StoryEntity::class).on(
                        path(StoryEntity::userId).eq(path(UserEntity::id))
                    ),
                    join(ProblemEntity::class).on(
                        path(ProblemEntity::storyId).eq(path(StoryEntity::id))
                    ),
                    join(AttemptEntity::class).on(
                        path(AttemptEntity::problemId).eq(path(ProblemEntity::id))
                    ),
                    join(VideoEntity::class).on(
                        path(VideoEntity::id).eq(path(AttemptEntity::videoId))
                    )
                ).where(
                    and(
                        path(UserEntity::id).eq(userId),
                        path(StoryEntity::date).between(
                            LocalDate.of(year, month, 1).minusDays(1),
                            LocalDate.of(year, month, 1).plusMonths(1)
                        )
                    )
                )
            }
        }
            .first()
            ?.toInt()
            ?: 0
    }

    override fun findRandomVideoBy(storyId: Long): Video? {
        val page = PageRequest.of(0, 1)

        return storyJpaRepository.findPage(page) {
            select(
                entity(VideoEntity::class)
            ).from(
                entity(VideoEntity::class),
                join(AttemptEntity::class).on(
                    path(AttemptEntity::videoId).eq(path(VideoEntity::id))
                ),
                join(ProblemEntity::class).on(
                    path(ProblemEntity::id).eq(path(AttemptEntity::problemId))
                ),
                join(StoryEntity::class).on(
                    path(StoryEntity::id).eq(path(ProblemEntity::storyId))
                )
            ).where(
                path(StoryEntity::id).eq(storyId)
            )
        }
            .first()
            ?.toDomain()
    }
}
