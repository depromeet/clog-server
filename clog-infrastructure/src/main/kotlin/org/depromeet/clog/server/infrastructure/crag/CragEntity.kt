package org.depromeet.clog.server.infrastructure.crag

import jakarta.persistence.*
import org.depromeet.clog.server.domain.crag.domain.Crag
import org.depromeet.clog.server.infrastructure.common.BaseEntity
import org.hibernate.annotations.Comment

@Entity
class CragEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Comment("암장 이름")
    @Column(nullable = false)
    val name: String,

    @Comment("도로명 주소")
    @Column(nullable = false)
    val roadAddress: String,

    @Embedded
    val coordinate: CoordinateEntity,

    @Comment("카카오맵 장소 ID")
    @Column(nullable = false)
    val kakaoPlaceId: Long
) : BaseEntity() {

    fun toDomain(): Crag = Crag(
        id = this.id,
        name = this.name,
        roadAddress = this.roadAddress,
        coordinate = this.coordinate.toDomain(),
        kakaoPlaceId = this.kakaoPlaceId
    )

    companion object {
        fun fromDomain(crag: Crag): CragEntity = CragEntity(
            name = crag.name,
            roadAddress = crag.roadAddress,
            coordinate = CoordinateEntity.fromDomain(crag.coordinate),
            kakaoPlaceId = crag.kakaoPlaceId
        )
    }
}
