package org.depromeet.clog.server.api.grade.application

import org.depromeet.clog.server.api.grade.presentation.dto.GetMyGradeInfoResponse
import org.depromeet.clog.server.api.grade.presentation.dto.toGetMyGradeInfoResponse
import org.depromeet.clog.server.domain.crag.domain.grade.Grade
import org.depromeet.clog.server.domain.crag.domain.grade.GradeRepository
import org.depromeet.clog.server.global.utils.dto.CursorPagination
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetMyGrade(
    private val gradeRepository: GradeRepository,
) {

    @Transactional(readOnly = true)
    fun getMyGrades(
        userId: Long,
        cursor: Long?,
        pageSize: Int
    ): CursorPagination.Response<Long, GetMyGradeInfoResponse> {
        val domainGrades: List<Grade> =
            gradeRepository.findDistinctGradesByUserId(userId, cursor, pageSize)
        val hasMore = domainGrades.size > pageSize
        val trimmedGrades = if (hasMore) domainGrades.subList(0, pageSize) else domainGrades
        val apiResponses = trimmedGrades.map { it.toGetMyGradeInfoResponse() }

        val nextCursor: Long? = if (hasMore) {
            trimmedGrades.last().id
        } else {
            null
        }

        return CursorPagination.Response(
            contents = apiResponses,
            meta = CursorPagination.Response.Meta(nextCursor = nextCursor, hasMore = hasMore)
        )
    }
}
