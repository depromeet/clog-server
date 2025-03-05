package org.depromeet.clog.server.domain.crag.domain

interface CragColorRepository {
    fun save(cragColor: CragColor): CragColor
}
