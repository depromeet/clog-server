package org.depromeet.clog.server.admin.api.crag.application

import org.depromeet.clog.server.admin.api.crag.presentation.dto.SaveCragColorDto
import org.depromeet.clog.server.domain.admin.ColorAdminRepository
import org.depromeet.clog.server.domain.crag.domain.color.Color
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SaveColor(
    private val colorAdminRepository: ColorAdminRepository
) {

    @Transactional
    operator fun invoke(
        request: SaveCragColorDto.Request
    ): SaveCragColorDto.Response {
        val color = Color(null, request.name, request.hex)
        val res = colorAdminRepository.save(color)

        return SaveCragColorDto.Response(
            name = res.name,
            hex = res.hex
        )
    }
}
