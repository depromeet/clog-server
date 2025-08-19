package org.depromeet.clog.server.domain.notification

import java.time.LocalDateTime

interface NotificationRepository {
    fun findRecentByUserId(
        userId: Long,
        from: LocalDateTime,
        page: Int,
        size: Int
    ): List<NotificationQuery>

    @Suppress("LongParameterList")
    fun findByUserIdAndType(
        userId: Long,
        type: NotificationType,
        from: LocalDateTime,
        page: Int,
        size: Int
    ): List<NotificationQuery>

    fun clearNewFlags(userId: Long)

    fun deleteByIdAndUserId(notificationId: Long, userId: Long): Int

    fun existsUnreadByUserId(userId: Long): Boolean

    fun markAsRead(userId: Long, notificationId: Long): Int
}
