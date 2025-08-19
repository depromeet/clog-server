package org.depromeet.clog.server.domain.notification

import java.time.LocalDateTime

data class NotificationQuery(
    val id: Long,
    val userId: Long,
    val type: NotificationType,
    val title: String,
    val message: String,
    val targetId: Long,
    val isRead: Boolean,
    val readAt: LocalDateTime?,
    val isNewFlagCleared: Boolean,
    val createdAt: LocalDateTime,
)
