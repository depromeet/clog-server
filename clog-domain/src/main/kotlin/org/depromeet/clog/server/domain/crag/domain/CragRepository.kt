package org.depromeet.clog.server.domain.crag.domain

interface CragRepository {
    fun save(crag: Crag): Crag
    fun saveAll(crags: List<Crag>): List<Crag>
    fun existsByKakaoPlaceId(kakaoPlaceId: Long): Boolean
}
