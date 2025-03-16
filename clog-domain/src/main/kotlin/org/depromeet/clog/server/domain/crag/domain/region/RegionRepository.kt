package org.depromeet.clog.server.domain.crag.domain.region

interface RegionRepository {
    fun findByRegionName(regionName: RegionName): List<Region>
}
