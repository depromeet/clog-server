package org.depromeet.clog.server.infrastructure.story

import jakarta.persistence.*
import org.depromeet.clog.server.domain.story.StoryStatus
import org.depromeet.clog.server.infrastructure.common.BaseEntity
import org.depromeet.clog.server.infrastructure.crag.CragEntity
import org.depromeet.clog.server.infrastructure.problem.ProblemEntity
import java.time.LocalDate

@Table(name = "story")
@Entity
class StoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = null,

    @Column(name = "user_id")
    val userId: Long? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "crag_id")
    val crag: CragEntity? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "varchar")
    val status: StoryStatus,

    @Column(name = "memo")
    val memo: String? = null,

    @Column(name = "date")
    val date: LocalDate,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "story")
    val problems: List<ProblemEntity>,
) : BaseEntity()
