package org.depromeet.clog.server.domain.crag.domain

interface RegionRepository {
    fun findByRegionName(regionName: RegionName): List<Region>
}
