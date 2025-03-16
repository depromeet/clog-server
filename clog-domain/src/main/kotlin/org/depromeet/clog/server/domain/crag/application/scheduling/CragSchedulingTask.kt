
import io.github.oshai.kotlinlogging.KotlinLogging
import org.depromeet.clog.server.domain.crag.domain.CragRepository
import org.depromeet.clog.server.domain.crag.domain.ExternalMapAdapter
import org.depromeet.clog.server.domain.crag.domain.region.RegionName
import org.depromeet.clog.server.domain.crag.domain.region.RegionRepository
import org.depromeet.clog.server.domain.crag.dto.KakaoSearchResponseDto
import org.springframework.stereotype.Component

@Component
class CragSchedulingTask(
    private val cragRepository: CragRepository,
    private val regionRepository: RegionRepository,
    private val externalMapAdapter: ExternalMapAdapter
) {

    private val logger = KotlinLogging.logger {}

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
            externalMapAdapter.searchKakaoLocationResult(query, page)?.let { response ->
                saveAndUpdateCrag(response)
            } ?: run {
                logger.info { "No response received" }
                isEnd = true
            }

            page++
        }
    }

    private fun saveAndUpdateCrag(response: KakaoSearchResponseDto) {
        val newCrags = response.documents
            .filterNot { cragRepository.existsByKakaoPlaceId(it.id) }
            .map { it.toDomain() }

        if (newCrags.isNotEmpty()) {
            cragRepository.saveAll(newCrags)
        }
    }
}
