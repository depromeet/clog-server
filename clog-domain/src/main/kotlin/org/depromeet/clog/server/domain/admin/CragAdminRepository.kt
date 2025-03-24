package org.depromeet.clog.server.domain.admin

import org.depromeet.clog.server.domain.crag.domain.Crag

interface CragAdminRepository {

    fun save(crag: Crag): Crag

    fun findById(id: Long): Crag?

    fun findAll(): List<Crag>
}
