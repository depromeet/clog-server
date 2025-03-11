package org.depromeet.clog.server.admin.domain.crag

import org.depromeet.clog.server.domain.crag.domain.Crag

interface CragAdminRepository {

    fun save(crag: Crag): Crag

    fun findById(id: Long): Crag?

    fun findAll(): List<Crag>
}
