package org.depromeet.clog.server.admin.api.crag.presentation.dto

object SaveCragDto {

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

        fun withDefaults(): Request {
            return Request(
                name = name.orStringDefault("이름 없음"),
                roadAddress = roadAddress.orStringDefault("주소 미확인"),
                longitude = longitude.orNumberDefault(0),
                latitude = latitude.orNumberDefault(0),
                kakaoPlaceId = kakaoPlaceId.orNumberDefault(0)
            )
        }

        private fun String?.orStringDefault(defaultValue: String): String {
            return if (this.isNullOrBlank()) defaultValue else this
        }

        private fun String?.orNumberDefault(defaultValue: Int): String {
            return this ?: defaultValue.toString()
        }
    }
}
