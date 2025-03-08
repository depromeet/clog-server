package org.depromeet.clog.server.domain.crag.domain

interface RegionRepository {
    fun save(region: Region): Region
}
