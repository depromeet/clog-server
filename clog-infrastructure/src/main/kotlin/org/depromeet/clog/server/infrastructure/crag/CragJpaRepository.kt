package org.depromeet.clog.server.infrastructure.crag

import org.springframework.data.jpa.repository.JpaRepository

interface CragJpaRepository : JpaRepository<CragEntity, Long> {
    fun existsByKakaoPlaceId(kakaoPlaceId: Long): Boolean
}
