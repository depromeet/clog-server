package org.depromeet.clog.server.admin.api.crag.application

import org.depromeet.clog.server.admin.api.crag.presentation.dto.CragResult
import org.depromeet.clog.server.domain.admin.CragAdminRepository
import org.depromeet.clog.server.domain.admin.GradeAdminRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetAllCrags(
    private val cragAdminRepository: CragAdminRepository,
    private val gradeAdminRepository: GradeAdminRepository
) {

    @Transactional(readOnly = true)
    operator fun invoke(): List<CragResult.WithGradeCount> {
        return cragAdminRepository.findAll().map { crag ->
            val gradeCount = gradeAdminRepository.findByCrag(crag).size
            CragResult.WithGradeCount(
                cragResult = CragResult.from(crag),
                gradeCount = gradeCount
            )
        }
    }
}
