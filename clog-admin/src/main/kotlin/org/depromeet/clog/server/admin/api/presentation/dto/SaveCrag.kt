package org.depromeet.clog.server.admin.api.presentation.dto

object SaveCrag {

    data class Request(
        val name: String = "",
        val roadAddress: String = "",
        val longitude: String = "",
        val latitude: String = "",
        val kakaoPlaceId: String = ""
    ) {

        fun sanitized(): Request {
            return Request(
                name = name.replace(",", ""),
                roadAddress = roadAddress.replace(",", ""),
                longitude = longitude.replace(",", ""),
                latitude = latitude.replace(",", ""),
                kakaoPlaceId = kakaoPlaceId.replace(",", "")
            )
        }
    }
}
