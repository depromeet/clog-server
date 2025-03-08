package org.depromeet.clog.server.domain.crag.domain

data class Region(
    val id: Long? = null,
    val regionName: RegionName,
    val district: String
)
