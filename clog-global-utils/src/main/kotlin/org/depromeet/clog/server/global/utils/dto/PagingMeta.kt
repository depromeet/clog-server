package org.depromeet.clog.server.global.utils.dto

data class PagingMeta(
    val nextCursor: Long?,
    val hasMore: Boolean
)

data class PagedResponse<T>(
    val contents: List<T>,
    val meta: PagingMeta
)
