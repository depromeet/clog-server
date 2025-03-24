package org.depromeet.clog.server.infrastructure.admin

import org.depromeet.clog.server.domain.admin.ColorAdminRepository
import org.depromeet.clog.server.domain.crag.domain.color.Color
import org.depromeet.clog.server.infrastructure.crag.ColorJpaRepository
import org.depromeet.clog.server.infrastructure.mappers.ColorMapper
import org.springframework.stereotype.Component

@Component
class ColorAdminAdapter(
    private val colorMapper: ColorMapper,
    private val colorJpaRepository: ColorJpaRepository
) : ColorAdminRepository {

    override fun save(color: Color): Color {
        val entity = colorJpaRepository.save(colorMapper.toEntity(color))
        return colorMapper.toDomain(entity)
    }

    override fun findAll(): List<Color> {
        return colorJpaRepository.findAll().map { colorMapper.toDomain(it) }
    }

    override fun findByNameAndHex(name: String, hex: String): Color? {
        val entity = colorJpaRepository.findByNameAndHex(name, hex)
        return colorMapper.toDomain(entity)
    }

    override fun findByNameOrHex(name: String, hex: String): Color? {
        val entity = colorJpaRepository.findByNameOrHex(name, hex)
        return entity?.let { colorMapper.toDomain(it) }
    }
}
