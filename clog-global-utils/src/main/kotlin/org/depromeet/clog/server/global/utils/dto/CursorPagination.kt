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

        @Parameter(example = "122.4194", description = "사용자의 경도(x)")
        val longitude: Double? = null,

        @Parameter(example = "37.7749", description = "사용자의 위도(y)")
        val latitude: Double? = null
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
