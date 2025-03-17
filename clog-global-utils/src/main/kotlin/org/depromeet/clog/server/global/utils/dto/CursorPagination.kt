package org.depromeet.clog.server.global.utils.dto

import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema

object CursorPagination {

    @Schema(description = "커서 기반 페이지네이션 요청")
    data class Request(
        @Parameter(example = "100000")
        val cursor: Long? = null,

        @Parameter(example = "10")
        val pageSize: Int = 10
    )

    @Schema(description = "커서 기반 페이지네이션 응답")
    data class Response<T>(
        val contents: List<T>,
        val meta: Meta
    ) {

        @Schema(description = "페이지네이션 메타 정보")
        data class Meta(
            val nextCursor: Long?,
            val hasMore: Boolean
        )
    }
}
