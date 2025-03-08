package org.depromeet.clog.server.domain.common

data class ClogApiResponse<T>(
    val data: T? = null,
) {
    companion object {
        fun <T> from(data: T): ClogApiResponse<T> {
            return ClogApiResponse(data = data)
        }
    }
}
