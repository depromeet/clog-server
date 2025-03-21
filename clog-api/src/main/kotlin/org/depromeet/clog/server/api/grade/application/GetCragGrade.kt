package org.depromeet.clog.server.api.grade.application

import org.depromeet.clog.server.api.grade.presentation.dto.GetGradeResponse
import org.depromeet.clog.server.api.grade.presentation.dto.GradesResponse
import org.depromeet.clog.server.domain.crag.domain.grade.GradeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetCragGrade(
    private val gradeRepository: GradeRepository
) {

    @Transactional(readOnly = true)
    operator fun invoke(
        cragId: Long,
    ): GradesResponse {
        val grades = gradeRepository.findGradesByCragId(cragId)

        return GradesResponse(
            grades.map { GetGradeResponse.from(it) }
        )
    }
}
