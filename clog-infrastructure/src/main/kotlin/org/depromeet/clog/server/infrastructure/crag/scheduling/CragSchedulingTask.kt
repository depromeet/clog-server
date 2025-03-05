package org.depromeet.clog.server.infrastructure.crag.scheduling

import com.fasterxml.jackson.annotation.JsonProperty
import io.github.oshai.kotlinlogging.KotlinLogging
import org.depromeet.clog.server.domain.crag.domain.Coordinate
import org.depromeet.clog.server.domain.crag.domain.Crag
import org.depromeet.clog.server.infrastructure.crag.CragEntity
import org.depromeet.clog.server.infrastructure.crag.CragJpaRepository
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestClient

@Component
class CragSchedulingTask(
    private val cragJpaRepository: CragJpaRepository,
    private val kakaoMapRestClient: RestClient
) {

    private val logger = KotlinLogging.logger {}

    data class KakaoSearchResponse(
        val documents: List<KakaoSearchDocument>,
        val meta: KakaoSearchMeta
    )

    data class KakaoSearchDocument(
        val id: Long,
        @JsonProperty("place_name") val placeName: String,
        @JsonProperty("road_address_name") val roadAddressName: String,
        val x: Double,
        val y: Double
    )

    data class KakaoSearchMeta(
        @JsonProperty("is_end") val isEnd: Boolean
    )

    companion object {
        const val MAX_PAGE_SIZE = 3
        const val LOCATION_BASE_QUERY = "클라이밍"
        val SEOUL_DISTRICTS = listOf(
            "강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구",
            "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구",
            "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구"
        )
    }

    // @Scheduled
    fun saveCragSchedule() {
        SEOUL_DISTRICTS.forEach { district ->
            searchAndSaveCragInDistrict(district)
        }
    }

    private fun searchAndSaveCragInDistrict(district: String) {
        var page = 1
        var isEnd = false
        val query = "$LOCATION_BASE_QUERY $district"

        while (!isEnd && page <= MAX_PAGE_SIZE) {
            searchKakaoLocationResult(query, page)?.let { response ->
                saveAndUpdateCrag(response)
            } ?: run {
                logger.info { "No response received for $district, page $page" }
                isEnd = true
            }

            page++
        }
    }

    private fun createParams(
        query: String,
        page: Int
    ): MultiValueMap<String, String> {
        val params: MultiValueMap<String, String> = LinkedMultiValueMap<String, String>().apply {
            add("query", query)
            add("page", page.toString())
        }

        return params
    }

    private fun searchKakaoLocationResult(
        query: String,
        page: Int
    ): KakaoSearchResponse? {
        return kakaoMapRestClient.get()
            .uri { it.queryParams(createParams(query, page)).build() }
            .retrieve()
            .body(KakaoSearchResponse::class.java)
    }

    private fun saveAndUpdateCrag(response: KakaoSearchResponse) {
        val newCrags = response.documents
            .filterNot { cragJpaRepository.existsByKakaoPlaceId(it.id) }
            .map { doc ->
                Crag(
                    name = doc.placeName,
                    roadAddress = doc.roadAddressName,
                    coordinate = Coordinate(
                        longitude = doc.x,
                        latitude = doc.y
                    ),
                    kakaoPlaceId = doc.id
                )
            }

        if (newCrags.isNotEmpty()) {
            cragJpaRepository.saveAll(newCrags.map { CragEntity.fromDomain(it) })
                .map { it.toDomain() }
        }
    }
}
