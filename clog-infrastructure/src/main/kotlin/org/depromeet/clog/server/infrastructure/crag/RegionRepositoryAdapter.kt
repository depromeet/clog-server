package org.depromeet.clog.server.infrastructure.crag

import org.depromeet.clog.server.domain.crag.domain.Region
import org.depromeet.clog.server.domain.crag.domain.RegionName
import org.depromeet.clog.server.domain.crag.domain.RegionRepository
import org.springframework.stereotype.Repository

@Repository
class RegionRepositoryAdapter(
    private val regionJpaRepository: RegionJpaRepository
) : RegionRepository {
    override fun findByRegionName(regionName: RegionName): List<Region> =
        regionJpaRepository.findByRegionName(regionName)
            .toList()
            .map { it.toDomain() }
}
