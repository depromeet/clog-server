package org.depromeet.clog.server.domain.common

data class ApiResponse<T>(
    val success: Boolean = true,
    val data: T? = null
) {
    companion object {
        fun <T> from(data: T): ApiResponse<T> {
            return ApiResponse(data = data)
        }

        fun success(): ApiResponse<Unit> {
            return ApiResponse(success = true, data = null)
        }
    }
}
