package org.depromeet.clog.server.api.grade.application

import org.depromeet.clog.server.api.grade.presentation.dto.GetMyGradeInfoResponse
import org.depromeet.clog.server.api.grade.presentation.dto.toGetMyGradeInfoResponse
import org.depromeet.clog.server.domain.crag.domain.Grade
import org.depromeet.clog.server.domain.story.StoryRepository
import org.depromeet.clog.server.global.utils.dto.PagedResponse
import org.depromeet.clog.server.global.utils.dto.PagingMeta
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetMyGrade(
    private val storyRepository: StoryRepository
) {
    @Transactional(readOnly = true)
    fun getMyGrades(
        userId: Long,
        cursor: Long?,
        pageSize: Int
    ): PagedResponse<GetMyGradeInfoResponse> {
        val domainGrades: List<Grade> =
            storyRepository.findDistinctGradesByUserId(userId, cursor, pageSize)
        val hasMore = domainGrades.size > pageSize
        val trimmedGrades = if (hasMore) domainGrades.subList(0, pageSize) else domainGrades
        val apiResponses = trimmedGrades.map { it.toGetMyGradeInfoResponse() }

        val nextCursor: Long? = if (hasMore) {
            trimmedGrades.last().id
        } else {
            null
        }

        return PagedResponse(
            contents = apiResponses,
            meta = PagingMeta(nextCursor = nextCursor, hasMore = hasMore)
        )
    }
}
