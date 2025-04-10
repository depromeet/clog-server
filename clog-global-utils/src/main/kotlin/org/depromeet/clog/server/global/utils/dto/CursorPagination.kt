package org.depromeet.clog.server.global.utils.dto

import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema

object CursorPagination {

    @Schema(description = "커서 페이지네이션 요청", name = "CursorPaginationGeneralRequest")
    data class GeneralRequest(
        @Parameter(example = "1", description = "커서 값 (예: 정수")
        val cursor: Long? = null,

        @Parameter(example = "10")
        val pageSize: Int = 10,
    )

    @Schema(description = "위치 기반 커서 페이지네이션 요청", name = "CursorPaginationLocationBasedRequest")
    data class LocationBasedRequest(
        @Parameter(example = "1", description = "커서 값 (예: 소수")
        val cursor: Double? = null,

        @Parameter(example = "10")
        val pageSize: Int = 10,
    )

    @Schema(description = "키워드와 위치 기반 커서 페이지네이션 요청", name = "CursorPaginationLocationBasedRequest")
    data class LocationBasedAndKeywordRequest(
        @Parameter(example = "1", description = "커서 값 (예: 소수")
        val cursor: Double? = null,

        @Parameter(example = "더클", description = "암장 키워드")
        val keyword: String? = null,

        @Parameter(example = "10")
        val pageSize: Int = 10,
    )

    @Schema(description = "커서 기반 페이지네이션 응답", name = "CursorPaginationResponse")
    data class Response<T, E>(
        val contents: List<E>,
        val meta: Meta<T>
    ) {

        @Schema(description = "페이지네이션 메타 정보")
        data class Meta<T>(
            val nextCursor: T?,
            val hasMore: Boolean
        )
    }
}
