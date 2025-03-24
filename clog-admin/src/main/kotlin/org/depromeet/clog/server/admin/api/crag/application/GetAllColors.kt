package org.depromeet.clog.server.admin.api.crag.application

import org.depromeet.clog.server.admin.api.crag.presentation.dto.ColorResult
import org.depromeet.clog.server.domain.admin.ColorAdminRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetAllColors(
    private val colorAdminRepository: ColorAdminRepository
) {

    @Transactional(readOnly = true)
    operator fun invoke(): List<ColorResult> {
        return colorAdminRepository.findAll().map { ColorResult.from(it) }
    }
}
