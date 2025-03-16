package org.depromeet.clog.server.infrastructure.problem

import jakarta.persistence.*
import org.depromeet.clog.server.infrastructure.attempt.AttemptEntity
import org.depromeet.clog.server.infrastructure.common.BaseEntity
import org.depromeet.clog.server.infrastructure.crag.GradeEntity
import org.depromeet.clog.server.infrastructure.story.StoryEntity

@Table(name = "problem")
@Entity
class ProblemEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "story_id")
    val story: StoryEntity,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "grade_id")
    val grade: GradeEntity? = null,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "problem")
    val attempts: List<AttemptEntity> = emptyList(),
) : BaseEntity()
