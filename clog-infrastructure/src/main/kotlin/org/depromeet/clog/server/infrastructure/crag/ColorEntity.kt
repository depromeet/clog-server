package org.depromeet.clog.server.infrastructure.crag

import jakarta.persistence.*
import org.depromeet.clog.server.domain.crag.domain.Color
import org.depromeet.clog.server.infrastructure.common.BaseEntity
import org.hibernate.annotations.Comment

@Table(name = "crag_color")
@Entity
class ColorEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Comment("암장 색상 이름")
    @Column(nullable = false, length = 50)
    val name: String
) : BaseEntity() {

    fun toDomain(): Color = Color(
        id = this.id,
        name = this.name
    )

    companion object {
        fun fromDomain(color: Color): ColorEntity = ColorEntity(
            name = color.name
        )
    }
}
