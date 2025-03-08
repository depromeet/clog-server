package org.depromeet.clog.server.infrastructure.kakaoMap

import org.depromeet.clog.server.domain.crag.domain.ExternalMapAdapter
import org.depromeet.clog.server.domain.crag.dto.KakaoSearchResponseDto
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestClient

@Component
class KakaoMapAdapter(
    private val kakaoMapRestClient: RestClient
) : ExternalMapAdapter {

    override fun searchKakaoLocationResult(
        query: String,
        page: Int
    ): KakaoSearchResponseDto? {
        return kakaoMapRestClient.get()
            .uri { it.queryParams(createParams(query, page)).build() }
            .retrieve()
            .body(KakaoSearchResponseDto::class.java)
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
}
