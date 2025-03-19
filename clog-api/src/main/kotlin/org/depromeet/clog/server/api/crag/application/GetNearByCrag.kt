package org.depromeet.clog.server.api.crag.application

import org.depromeet.clog.server.api.crag.presentation.dto.CragResponse
import org.depromeet.clog.server.domain.crag.domain.CragRepository
import org.depromeet.clog.server.domain.crag.domain.Location
import org.depromeet.clog.server.global.utils.dto.CursorPagination.PointResponse
import org.depromeet.clog.server.global.utils.dto.CursorPagination.PointResponse.Meta
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetNearByCrag(
    private val cragRepository: CragRepository
) {

    @Transactional(readOnly = true)
    operator fun invoke(
        cursor: Double?,
        pageSize: Int,
        longitude: Double?,
        latitude: Double?
    ): PointResponse<CragResponse> {
        val crags = cragRepository.findNearCragsByLocation(Location(longitude!!, latitude!!), cursor, pageSize)

        val hasMore = crags.size > pageSize
        val result = if (hasMore) crags.take(pageSize) else crags
        val contents = (if (hasMore) crags.take(pageSize) else crags).map { CragResponse.from(it.first) }
        val nextCursor = if (hasMore) result.last().second else null

        return PointResponse(
            contents = contents,
            meta = Meta(nextCursor = nextCursor, hasMore = hasMore)
        )
    }
}
