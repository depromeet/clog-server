package org.depromeet.clog.server.domain.notification

import java.time.LocalDateTime

interface NotificationRepository {
    fun findRecentByUserId(
        userId: Long,
        from: LocalDateTime,
        page: Int,
        size: Int
    ): List<NotificationQuery>

    fun clearNewFlags(userId: Long)
}
