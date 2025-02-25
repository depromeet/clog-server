package org.depromeet.clog.server.domain.common

data class ApiResponse<T>(
    val success: Boolean = true,
    val data: T? = null,
    val error: ErrorResponse? = null
) {

    data class ErrorResponse(
        val name: String,
        val code: String,
        val message: String? = null,
        val detail: Map<String, Any>? = null
    ) {
        companion object {
            fun from(errorCode: ErrorCode, detail: Map<String, Any>? = null): ErrorResponse {
                return ErrorResponse(
                    name = errorCode.name,
                    code = errorCode.code,
                    message = errorCode.message,
                    detail = detail
                )
            }
        }
    }

    companion object {
        fun <T> success(data: T): ApiResponse<T> {
            return ApiResponse(success = true, data = data)
        }

        fun <T> fail(exception: ClogException, detail: Map<String, Any>? = null): ApiResponse<T> {
            val response = ErrorResponse.from(exception.errorCode, detail)
            return ApiResponse(success = false, error = response)
        }
    }
}
