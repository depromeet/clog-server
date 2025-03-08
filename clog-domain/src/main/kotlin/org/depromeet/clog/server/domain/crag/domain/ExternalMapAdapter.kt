package org.depromeet.clog.server.domain.crag.domain

import org.depromeet.clog.server.domain.crag.dto.KakaoSearchResponseDto

interface ExternalMapAdapter {
    fun searchKakaoLocationResult(query: String, page: Int): KakaoSearchResponseDto?
}
