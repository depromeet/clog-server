package org.depromeet.clog.server.infrastructure.attempt

import jakarta.persistence.*
import org.depromeet.clog.server.domain.attempt.AttemptStatus
import org.depromeet.clog.server.infrastructure.common.BaseEntity
import org.depromeet.clog.server.infrastructure.problem.ProblemEntity
import org.depromeet.clog.server.infrastructure.video.VideoEntity

@Table(name = "attempt")
@Entity
class AttemptEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    val problem: ProblemEntity,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    val video: VideoEntity,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    val status: AttemptStatus,
) : BaseEntity()
