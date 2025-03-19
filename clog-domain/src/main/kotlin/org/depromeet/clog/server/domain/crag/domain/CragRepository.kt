package org.depromeet.clog.server.domain.crag.domain

interface CragRepository {
    fun save(crag: Crag): Crag

    fun findById(id: Long): Crag?

    fun saveAll(crags: List<Crag>): List<Crag>

    fun existsByKakaoPlaceId(kakaoPlaceId: Long): Boolean

    fun findDistinctCragsByUserId(userId: Long, cursor: Long?, pageSize: Int): List<Crag>

    fun findNearCragsByLocation(location: Location, cursor: Double?, pageSize: Int): List<Pair<Crag, Double>>
}
