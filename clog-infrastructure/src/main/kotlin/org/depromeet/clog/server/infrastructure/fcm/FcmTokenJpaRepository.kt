package org.depromeet.clog.server.infrastructure.fcm

import org.springframework.data.jpa.repository.JpaRepository

interface FcmTokenJpaRepository : JpaRepository<FcmTokenEntity, Long> {
    fun findByUserId(userId: Long): FcmTokenEntity?
}
