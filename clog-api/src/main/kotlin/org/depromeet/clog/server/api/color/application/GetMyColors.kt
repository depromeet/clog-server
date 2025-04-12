package org.depromeet.clog.server.api.color.application

import org.depromeet.clog.server.api.crag.presentation.dto.GetMyColorResponse
import org.depromeet.clog.server.api.crag.presentation.dto.toGetMyColorResponse
import org.depromeet.clog.server.domain.crag.domain.color.ColorRepository
import org.depromeet.clog.server.global.utils.dto.CursorPagination
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetMyColors(
    private val colorRepository: ColorRepository,
) {

    @Transactional(readOnly = true)
    operator fun invoke(
        userId: Long,
        cursor: Long?,
        pageSize: Int,
    ): CursorPagination.Response<Long, GetMyColorResponse> {
        val colors = colorRepository.findDistinctColorsByUserId(userId, cursor, pageSize)
        val hasMore = colors.size > pageSize
        val trimmedGrades = if (hasMore) colors.subList(0, pageSize) else colors
        val apiResponses = trimmedGrades.map { it.toGetMyColorResponse() }

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
