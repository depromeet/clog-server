package org.depromeet.clog.server.infrastructure.crag

import org.depromeet.clog.server.domain.crag.domain.color.Color
import org.depromeet.clog.server.domain.crag.domain.color.ColorRepository
import org.depromeet.clog.server.infrastructure.mappers.ColorMapper
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository

@Repository
class ColorAdapter(
    private val colorJpaRepository: ColorJpaRepository,
    private val colorMapper: ColorMapper,
) : ColorRepository {
    override fun findDistinctColorsByUserId(userId: Long, cursor: Long?, pageSize: Int): List<Color> {
        val pageable = PageRequest.of(0, pageSize + 1)
        return colorJpaRepository.findDistinctColorsByUserIdWithCursor(userId, cursor, pageable)
            .map { colorMapper.toDomain(it) }
    }
}
