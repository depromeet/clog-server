package org.depromeet.clog.server.domain.common

data class ApiResponse<T>(
    val data: T? = null
) {
    companion object {
        fun <T> from(data: T): ApiResponse<T> {
            return ApiResponse(data = data)
        }
    }
}
