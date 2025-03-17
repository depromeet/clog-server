package org.depromeet.clog.server.api.crag.application

import org.depromeet.clog.server.api.crag.presentation.dto.CragResponse
import org.depromeet.clog.server.api.crag.presentation.dto.toGetMyCragInfoResponse
import org.depromeet.clog.server.domain.crag.domain.Crag
import org.depromeet.clog.server.domain.crag.domain.CragRepository
import org.depromeet.clog.server.global.utils.dto.CursorPagination.Response
import org.depromeet.clog.server.global.utils.dto.CursorPagination.Response.Meta
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetMyCrag(
    private val cragRepository: CragRepository,
) {
    @Transactional(readOnly = true)
    fun getMyCrags(
        userId: Long,
        cursor: Long?,
        pageSize: Int
    ): Response<CragResponse> {
        val domainCrags: List<Crag> =
            cragRepository.findDistinctCragsByUserId(userId, cursor, pageSize + 1)
        val hasMore = domainCrags.size > pageSize
        val trimmedCrags = if (hasMore) domainCrags.subList(0, pageSize) else domainCrags
        val apiResponses = trimmedCrags.map { it.toGetMyCragInfoResponse() }
        val nextCursor: Long? = if (hasMore) trimmedCrags.last().id else null

        return Response(
            contents = apiResponses,
            meta = Meta(nextCursor = nextCursor, hasMore = hasMore)
        )
    }
}
