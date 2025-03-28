package org.depromeet.clog.server.domain.crag.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import org.depromeet.clog.server.domain.crag.domain.Crag
import org.depromeet.clog.server.domain.crag.domain.Location

data class KakaoSearchResponseDto(
    val documents: List<KakaoSearchDocument>,
    val meta: KakaoSearchMeta
) {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class KakaoSearchDocument(
        val id: Long,
        val placeName: String,
        val roadAddressName: String,
        val addressName: String,
        val x: Double,
        val y: Double
    ) {
        fun toDomain(): Crag = Crag(
            name = this.placeName,
            roadAddress = this.roadAddressName,
            lotNumberAddress = addressName,
            location = Location(
                longitude = this.x,
                latitude = this.y
            ),
            kakaoPlaceId = this.id
        )
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class KakaoSearchMeta(
        val isEnd: Boolean
    )
}
