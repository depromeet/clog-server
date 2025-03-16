package org.depromeet.clog.server.infrastructure.crag

import jakarta.persistence.*
import org.depromeet.clog.server.domain.crag.domain.region.Region
import org.depromeet.clog.server.domain.crag.domain.region.RegionName
import org.depromeet.clog.server.infrastructure.common.BaseEntity

@Table(name = "region")
@Entity
class RegionEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val regionName: RegionName,

    @Column(nullable = false)
    val district: String
) : BaseEntity() {

    fun toDomain(): Region = Region(
        id = this.id,
        regionName = this.regionName,
        district = this.district
    )

    companion object {
        fun fromDomain(region: Region): RegionEntity = RegionEntity(
            regionName = region.regionName,
            district = region.district
        )
    }
}
