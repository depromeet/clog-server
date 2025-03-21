package org.depromeet.clog.server.global.utils.dto

import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema

object CursorPagination {

    interface CursorRequest<T> {
        val cursor: T?
        val pageSize: Int
    }

    @Schema(description = "커서 페이지네이션 요청", name = "CursorPaginationGeneralRequest")
    data class GeneralRequest<T>(
        @Parameter(example = "1", description = "커서 값 (예: 정수 또는 소수")
        override val cursor: T? = null,

        @Parameter(example = "10")
        override val pageSize: Int = 10,
    ) : CursorRequest<T>

    @Schema(description = "위치 기반 커서 페이지네이션 요청", name = "CursorPaginationLocationBasedRequest")
    data class LocationBasedRequest<T>(
        @Parameter(example = "1", description = "커서 값 (예: 정수 또는 소수")
        override val cursor: T? = null,

        @Parameter(example = "10")
        override val pageSize: Int = 10,

        @Parameter(example = "122.4194", description = "사용자의 경도(x)")
        val longitude: Double? = null,

        @Parameter(example = "37.7749", description = "사용자의 위도(y)")
        val latitude: Double? = null
    ) : CursorRequest<T>

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
