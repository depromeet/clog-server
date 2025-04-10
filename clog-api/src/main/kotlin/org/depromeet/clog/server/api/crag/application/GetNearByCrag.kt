package org.depromeet.clog.server.api.crag.application

import org.depromeet.clog.server.api.crag.presentation.dto.CragResponse
import org.depromeet.clog.server.domain.crag.domain.CragRepository
import org.depromeet.clog.server.domain.crag.domain.Location
import org.depromeet.clog.server.global.utils.dto.CursorPagination
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetNearByCrag(
    private val cragRepository: CragRepository
) {

    @Transactional(readOnly = true)
    operator fun invoke(
        request: CursorPagination.LocationBasedRequest,
        longitude: Double,
        latitude: Double
    ): CursorPagination.Response<Double, CragResponse> {
        val crags = cragRepository.findNearCragsByLocation(
            Location(longitude, latitude),
            request.cursor,
            request.pageSize,
            request.keyword
        )

        val hasMore = crags.size > request.pageSize
        val contents = (if (hasMore) crags.take(request.pageSize) else crags).map { CragResponse.from(it.first) }
        val nextCursor = if (hasMore) crags.last().second else null

        return CursorPagination.Response(
            contents = contents,
            meta = CursorPagination.Response.Meta(nextCursor = nextCursor, hasMore = hasMore)
        )
    }
}
