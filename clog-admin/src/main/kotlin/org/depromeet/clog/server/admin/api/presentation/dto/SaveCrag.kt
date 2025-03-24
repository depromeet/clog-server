package org.depromeet.clog.server.admin.api.presentation.dto

object SaveCrag {

    data class Request(
        val name: String? = null,
        val roadAddress: String? = null,
        val longitude: String? = null,
        val latitude: String? = null,
        val kakaoPlaceId: String? = null
    ) {

        fun sanitized(): Request {
            return Request(
                name = name?.replace(",", ""),
                roadAddress = roadAddress?.replace(",", ""),
                longitude = longitude?.replace(",", ""),
                latitude = latitude?.replace(",", ""),
                kakaoPlaceId = kakaoPlaceId?.replace(",", "")
            )
        }
    }
}
