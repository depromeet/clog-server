package org.depromeet.clog.server.admin.api.crag.application

import jakarta.persistence.EntityNotFoundException
import org.depromeet.clog.server.admin.api.crag.presentation.dto.GradeResult
import org.depromeet.clog.server.domain.admin.CragAdminRepository
import org.depromeet.clog.server.domain.admin.GradeAdminRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetGrades(
    private val cragAdminRepository: CragAdminRepository,
    private val gradeAdminRepository: GradeAdminRepository
) {

    @Transactional(readOnly = true)
    operator fun invoke(
        id: Long
    ): List<GradeResult> {
        val crag = cragAdminRepository.findById(id)
            ?: throw EntityNotFoundException("Crag with ID $id not found")

        return gradeAdminRepository.findByCrag(crag).map { GradeResult.from(it) }
    }
}
