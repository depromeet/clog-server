package org.depromeet.clog.server.infrastructure.mappers

import org.depromeet.clog.server.domain.crag.domain.color.Color
import org.depromeet.clog.server.infrastructure.crag.ColorEntity
import org.springframework.stereotype.Component

@Component
class ColorMapper : DomainEntityMapper<Color, Color, ColorEntity> {

    override fun toDomain(entity: ColorEntity): Color {
        return Color(
            id = entity.id!!,
            name = entity.name,
            hex = entity.hex,
        )
    }

    override fun toEntity(domain: Color): ColorEntity {
        return ColorEntity(
            id = domain.id,
            name = domain.name,
            hex = domain.hex,
        )
    }
}
