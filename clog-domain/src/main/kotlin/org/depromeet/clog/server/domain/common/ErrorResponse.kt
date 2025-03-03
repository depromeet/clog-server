package org.depromeet.clog.server.domain.common

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
