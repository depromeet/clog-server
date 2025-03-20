package org.depromeet.clog.server.infrastructure.crag

import org.depromeet.clog.server.domain.crag.domain.region.Region
import org.depromeet.clog.server.domain.crag.domain.region.RegionName
import org.depromeet.clog.server.domain.crag.domain.region.RegionRepository
import org.springframework.stereotype.Repository

@Repository
class RegionAdapter(
    private val regionJpaRepository: RegionJpaRepository
) : RegionRepository {

    override fun findByRegionName(regionName: RegionName): List<Region> =
        regionJpaRepository.findByRegionName(regionName)
            .toList()
            .map { it.toDomain() }
}
