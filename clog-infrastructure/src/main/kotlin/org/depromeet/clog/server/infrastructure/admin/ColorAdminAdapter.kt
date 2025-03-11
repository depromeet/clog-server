package org.depromeet.clog.server.infrastructure.admin

import org.depromeet.clog.server.admin.domain.crag.ColorAdminRepository
import org.depromeet.clog.server.domain.crag.domain.Color
import org.depromeet.clog.server.infrastructure.crag.ColorEntity
import org.depromeet.clog.server.infrastructure.crag.ColorJpaRepository
import org.springframework.stereotype.Component

@Component
class ColorAdminAdapter(
    private val colorJpaRepository: ColorJpaRepository
) : ColorAdminRepository {

    override fun save(color: Color): Color {
        return colorJpaRepository.save(ColorEntity.fromDomain(color)).toDomain()
    }

    override fun findAll(): List<Color> {
        return colorJpaRepository.findAll().map { it.toDomain() }
    }

    override fun findByNameAndHex(name: String, hex: String): Color? {
        return colorJpaRepository.findByNameAndHex(name, hex).toDomain()
    }
}
