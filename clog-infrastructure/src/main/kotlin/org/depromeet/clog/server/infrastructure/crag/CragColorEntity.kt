package org.depromeet.clog.server.infrastructure.crag

import jakarta.persistence.*
import org.depromeet.clog.server.domain.crag.domain.CragColor
import org.depromeet.clog.server.infrastructure.common.BaseEntity

@Table(name = "crag_color_mapping")
@Entity
class CragColorEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val cragId: Long,

    @Column(nullable = false)
    val colorId: Long
) : BaseEntity() {

    fun toDomain(): CragColor = CragColor(
        id = this.id,
        cragId = this.cragId,
        colorId = this.colorId
    )

    companion object {
        fun fromDomain(cragColor: CragColor): CragColorEntity = CragColorEntity(
            cragId = cragColor.cragId,
            colorId = cragColor.colorId
        )
    }
}
