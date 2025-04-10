package org.depromeet.clog.server.infrastructure.crag

import jakarta.persistence.*
import org.depromeet.clog.server.domain.crag.domain.CragStatus
import org.depromeet.clog.server.infrastructure.common.BaseEntity
import org.hibernate.annotations.Comment
import org.locationtech.jts.geom.Point

@Table(name = "crag")
@Entity
class CragEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Comment("암장 이름")
    @Column(nullable = false)
    val name: String,

    @Comment("암장 상태")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar")
    val status: CragStatus,

    @Comment("도로명 주소")
    @Column(nullable = false)
    val roadAddress: String,

    @Comment("지번 주소")
    @Column(nullable = false)
    val lotNumberAddress: String,

    @Comment("위도/경도를 포함한 공간 데이터")
    @Column(columnDefinition = "GEOMETRY", nullable = false)
    var location: Point,

    @Comment("카카오맵 장소 ID")
    @Column(nullable = false)
    val kakaoPlaceId: Long,

    @OneToMany(mappedBy = "crag")
    val grades: List<GradeEntity> = emptyList(),
) : BaseEntity()
