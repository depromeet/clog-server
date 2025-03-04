package org.depromeet.clog.server.domain.crag.domain

interface CragRepository {
    fun save(crag: Crag): Crag
}
