package org.depromeet.clog.server.infrastructure.crag

import org.depromeet.clog.server.domain.crag.domain.RegionName
import org.springframework.data.jpa.repository.JpaRepository

interface RegionJpaRepository : JpaRepository<RegionEntity, Long> {
    fun findByRegionName(regionName: RegionName): List<RegionEntity>
}
