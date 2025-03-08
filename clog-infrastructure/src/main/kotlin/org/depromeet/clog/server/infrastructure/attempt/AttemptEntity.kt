package org.depromeet.clog.server.infrastructure.attempt

import jakarta.persistence.*
import org.depromeet.clog.server.domain.attempt.Attempt
import org.depromeet.clog.server.domain.attempt.AttemptStatus
import org.depromeet.clog.server.infrastructure.common.BaseEntity

@Table(name = "attempt")
@Entity
class AttemptEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = null,

    @Column(name = "problem_id")
    val problemId: Long,

    @Column(name = "video_id")
    val videoId: Long,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    val status: AttemptStatus,
) : BaseEntity() {
    fun toDomain(): Attempt {
        return Attempt(
            id = id,
            problemId = problemId,
            videoId = videoId,
            status = status,
        )
    }

    companion object {
        fun fromDomain(domain: Attempt): AttemptEntity {
            return AttemptEntity(
                id = domain.id,
                problemId = domain.problemId,
                videoId = domain.videoId,
                status = domain.status,
            )
        }
    }
}
