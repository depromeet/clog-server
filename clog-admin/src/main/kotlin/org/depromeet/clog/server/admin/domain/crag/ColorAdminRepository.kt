package org.depromeet.clog.server.admin.domain.crag

import org.depromeet.clog.server.domain.crag.domain.Color

interface ColorAdminRepository {

    fun save(color: Color): Color

    fun findAll(): List<Color>

    fun findByNameAndHex(name: String, hex: String): Color?
}
