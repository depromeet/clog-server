package org.depromeet.clog.server.infrastructure.story

import jakarta.persistence.*
import org.depromeet.clog.server.domain.story.Story
import org.depromeet.clog.server.infrastructure.common.BaseEntity
import java.time.LocalDate

@Table(name = "story")
@Entity
class StoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = null,

    @Column(name = "user_id")
    val userId: Long,

    @Column(name = "crag_id")
    val cragId: Long? = null,

    @Column(name = "memo")
    val memo: String? = null,

    @Column(name = "date")
    val date: LocalDate,
) : BaseEntity() {
    fun toDomain(): Story {
        return Story(
            id = id,
            userId = userId,
            cragId = cragId,
            date = date,
            memo = memo,
            createdAt = createdAt,
            updatedAt = modifiedAt,
        )
    }

    companion object {
        fun from(story: Story): StoryEntity {
            return StoryEntity(
                id = story.id,
                userId = story.userId,
                cragId = story.cragId,
                date = story.date,
                memo = story.memo,
            )
        }
    }
}
