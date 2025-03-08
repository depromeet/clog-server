package org.depromeet.clog.server.infrastructure.problem

import jakarta.persistence.*
import org.depromeet.clog.server.domain.attempt.Attempt
import org.depromeet.clog.server.domain.problem.Problem
import org.depromeet.clog.server.infrastructure.common.BaseEntity

@Table(name = "problem")
@Entity
class ProblemEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = null,

    @Column(name = "story_id")
    val storyId: Long,

    @Column(name = "grade_id")
    val gradeId: Long? = null,
) : BaseEntity() {

    fun toDomain(attempts: List<Attempt> = emptyList()): Problem {
        return Problem(
            id = id,
            storyId = storyId,
            gradeId = gradeId,
            attempts = attempts,
        )
    }

    companion object {
        fun fromDomain(domain: Problem): ProblemEntity {
            return ProblemEntity(
                id = domain.id,
                storyId = domain.storyId,
                gradeId = domain.gradeId,
            )
        }
    }
}
