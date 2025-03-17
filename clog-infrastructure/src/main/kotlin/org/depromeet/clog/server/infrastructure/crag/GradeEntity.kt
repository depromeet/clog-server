package org.depromeet.clog.server.infrastructure.crag

import jakarta.persistence.*

@Table(name = "grade")
@Entity
class GradeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = null,

    @JoinColumn(name = "color_id")
    @ManyToOne(fetch = FetchType.EAGER)
    val color: ColorEntity,

    @Column(name = "`order`")
    val order: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crag_id")
    val crag: CragEntity,
)
