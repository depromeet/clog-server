package org.depromeet.clog.server.admin.api.presentation.dto

import jakarta.validation.constraints.NotBlank

object SaveCrag {

    data class Request(
        @field:NotBlank(message = "crag name must not be blank")
        val name: String,

        val roadAddress: String,

        @field:NotBlank(message = "crag longitude must not be blank")
        val longitude: String,

        @field:NotBlank(message = "crag latitude must not be blank")
        val latitude: String,

        @field:NotBlank(message = "crag kakaoPlaceId must not be blank")
        val kakaoPlaceId: String
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
