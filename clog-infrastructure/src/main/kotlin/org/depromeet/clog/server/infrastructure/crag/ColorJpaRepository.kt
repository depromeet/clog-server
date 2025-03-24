package org.depromeet.clog.server.infrastructure.crag

import org.springframework.data.jpa.repository.JpaRepository

interface ColorJpaRepository : JpaRepository<ColorEntity, Long> {

    fun findByNameAndHex(name: String, hex: String): ColorEntity

    fun findByNameOrHex(name: String, hex: String): ColorEntity?
}
