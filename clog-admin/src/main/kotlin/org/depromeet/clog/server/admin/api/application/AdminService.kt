package org.depromeet.clog.server.admin.api.application

import jakarta.persistence.EntityNotFoundException
import org.depromeet.clog.server.admin.api.presentation.dto.*
import org.depromeet.clog.server.admin.domain.crag.ColorAdminRepository
import org.depromeet.clog.server.admin.domain.crag.CragAdminRepository
import org.depromeet.clog.server.admin.domain.crag.GradeAdminRepository
import org.depromeet.clog.server.domain.crag.domain.Crag
import org.depromeet.clog.server.domain.crag.domain.Location
import org.depromeet.clog.server.domain.crag.domain.color.Color
import org.depromeet.clog.server.domain.crag.domain.grade.Grade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdminService(
    private val cragAdminRepository: CragAdminRepository,
    private val colorAdminRepository: ColorAdminRepository,
    private val gradeAdminRepository: GradeAdminRepository
) {

    @Transactional(readOnly = true)
    fun getAllCrag(): List<CragResult.WithGradeCount> {
        return cragAdminRepository.findAll().map { crag ->
            val gradeCount = gradeAdminRepository.findByCrag(crag).size
            CragResult.WithGradeCount(
                cragResult = CragResult.from(crag),
                gradeCount = gradeCount
            )
        }
    }

    @Transactional
    fun createCrag(
        request: SaveCrag.Request
    ) {
        val location = Location(request.longitude.toDouble(), request.latitude.toDouble())
        val crag = Crag(null, request.name, request.roadAddress, location, request.kakaoPlaceId.toLong())

        cragAdminRepository.save(crag)
    }

    @Transactional(readOnly = true)
    fun getCrag(
        id: Long
    ): CragResult {
        val crag = cragAdminRepository.findById(id)
            ?: throw EntityNotFoundException("Crag with ID $id not found")

        return CragResult.from(crag)
    }

    @Transactional(readOnly = true)
    fun getAllColor(): List<ColorResult> {
        return colorAdminRepository.findAll().map { ColorResult.from(it) }
    }

    @Transactional
    fun createColor(
        request: SaveCragColor.Request
    ): SaveCragColor.Response {
        val color = Color(null, request.name, request.hex)
        val res = colorAdminRepository.save(color)

        return SaveCragColor.Response(
            name = res.name,
            hex = res.hex
        )
    }

    @Transactional
    fun createGrade(
        cragId: Long,
        request: SaveCragGrade.Request
    ): SaveCragGrade.Response {
        val color: Color = colorAdminRepository.findByNameAndHex(request.colorName, request.colorHex)
            ?: throw EntityNotFoundException("Color ${request.colorName} not found")

        val crag: Crag = cragAdminRepository.findById(cragId)
            ?: throw NoSuchElementException("Crag not found with id: $cragId")

        val grade = Grade(null, crag.id!!, color, request.gradeOrder.toInt())
        val res = gradeAdminRepository.save(grade)

        return SaveCragGrade.Response(
            colorName = res.color.name,
            colorHex = res.color.hex,
            gradeOrder = res.order
        )
    }

    @Transactional(readOnly = true)
    fun getGrades(
        id: Long
    ): List<GradeResult> {
        val crag = cragAdminRepository.findById(id)
            ?: throw EntityNotFoundException("Crag with ID $id not found")

        return gradeAdminRepository.findByCrag(crag).map { GradeResult.from(it) }
    }
}
