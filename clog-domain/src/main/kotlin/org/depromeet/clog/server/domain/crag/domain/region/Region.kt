package org.depromeet.clog.server.domain.crag.domain.region

data class Region(
    val id: Long? = null,
    val regionName: RegionName,
    val district: String
)
