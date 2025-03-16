package org.depromeet.clog.server.infrastructure.crag

import jakarta.persistence.*
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
    val name: String,

    @Comment("암장 색상 HEX 코드")
    @Column(nullable = false, length = 7)
    val hex: String,
) : BaseEntity()
