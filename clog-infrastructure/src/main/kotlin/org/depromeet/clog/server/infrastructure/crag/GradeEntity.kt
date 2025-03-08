package org.depromeet.clog.server.infrastructure.crag

import jakarta.persistence.*
import org.depromeet.clog.server.domain.crag.domain.Grade

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

    @Column(name = "order")
    val order: Int? = null,
) {

    fun toDomain(): Grade {
        return Grade(
            id = id,
            color = color.toDomain(),
            order = order,
        )
    }

    companion object {
        fun fromDomain(grade: Grade): GradeEntity {
            return GradeEntity(
                id = grade.id,
                color = ColorEntity.fromDomain(grade.color),
                order = grade.order,
            )
        }
    }
}
