package org.depromeet.clog.server.infrastructure.crag

import jakarta.persistence.*
import org.depromeet.clog.server.domain.crag.domain.RegionName

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
)
