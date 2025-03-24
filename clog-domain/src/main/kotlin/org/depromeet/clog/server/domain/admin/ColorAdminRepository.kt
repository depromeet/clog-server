package org.depromeet.clog.server.domain.admin

import org.depromeet.clog.server.domain.crag.domain.color.Color

interface ColorAdminRepository {

    fun save(color: Color): Color

    fun findAll(): List<Color>

    fun findByNameAndHex(name: String, hex: String): Color?

    fun findByNameOrHex(name: String, hex: String): Color?
}
