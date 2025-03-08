
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import io.github.oshai.kotlinlogging.KotlinLogging
import org.depromeet.clog.server.domain.crag.domain.*
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestClient

@Component
class CragSchedulingTask(
    private val cragRepository: CragRepository,
    private val regionRepository: RegionRepository,
    private val kakaoMapRestClient: RestClient
) {

    private val logger = KotlinLogging.logger {}

    data class KakaoSearchResponse(
        val documents: List<KakaoSearchDocument>,
        val meta: KakaoSearchMeta
    )

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class KakaoSearchDocument(
        val id: Long,
        val placeName: String,
        val roadAddressName: String,
        val x: Double,
        val y: Double
    ) {
        fun toDomain(): Crag = Crag(
            name = this.placeName,
            roadAddress = this.roadAddressName,
            coordinate = Coordinate(
                longitude = this.x,
                latitude = this.y
            ),
            kakaoPlaceId = this.id
        )
    }

    data class KakaoSearchMeta(
        @JsonProperty("is_end") val isEnd: Boolean
    )

    companion object {
        const val MAX_PAGE_SIZE = 3
        const val LOCATION_BASE_QUERY = "클라이밍"
    }

    // @Scheduled
    fun saveCragSchedule() {
        RegionName.entries.forEach { regionName ->
            regionRepository.findByRegionName(regionName).forEach { region ->
                val query = "$LOCATION_BASE_QUERY ${regionName.regionName} ${region.district}"
                searchAndSaveCragInDistrict(query)
            }
        }
    }

    private fun searchAndSaveCragInDistrict(query: String) {
        var page = 1
        var isEnd = false

        while (!isEnd && page <= MAX_PAGE_SIZE) {
            searchKakaoLocationResult(query, page)?.let { response ->
                saveAndUpdateCrag(response)
            } ?: run {
                logger.info { "No response received" }
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
            .filterNot { cragRepository.existsByKakaoPlaceId(it.id) }
            .map { it.toDomain() }

        if (newCrags.isNotEmpty()) {
            cragRepository.saveAll(newCrags)
        }
    }
}
