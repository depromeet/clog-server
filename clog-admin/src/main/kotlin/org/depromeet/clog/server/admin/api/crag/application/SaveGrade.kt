package org.depromeet.clog.server.admin.api.crag.application

import jakarta.persistence.EntityNotFoundException
import org.depromeet.clog.server.admin.api.crag.presentation.dto.SaveCragGradeDto
import org.depromeet.clog.server.domain.admin.ColorAdminRepository
import org.depromeet.clog.server.domain.admin.CragAdminRepository
import org.depromeet.clog.server.domain.admin.GradeAdminRepository
import org.depromeet.clog.server.domain.crag.domain.Crag
import org.depromeet.clog.server.domain.crag.domain.color.Color
import org.depromeet.clog.server.domain.crag.domain.grade.Grade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SaveGrade(
    private val cragAdminRepository: CragAdminRepository,
    private val colorAdminRepository: ColorAdminRepository,
    private val gradeAdminRepository: GradeAdminRepository
) {

    @Transactional
    operator fun invoke(
        cragId: Long,
        request: SaveCragGradeDto.Request
    ): SaveCragGradeDto.Response {
        val color: Color = colorAdminRepository.findByNameAndHex(request.colorName, request.colorHex)
            ?: throw EntityNotFoundException("Color ${request.colorName} not found")

        val crag: Crag = cragAdminRepository.findById(cragId)
            ?: throw NoSuchElementException("Crag not found with id: $cragId")

        val grade = Grade(null, crag.id!!, color, request.gradeOrder.toInt())
        val res = gradeAdminRepository.save(grade)

        return SaveCragGradeDto.Response(
            colorName = res.color.name,
            colorHex = res.color.hex,
            gradeOrder = res.order
        )
    }
}
