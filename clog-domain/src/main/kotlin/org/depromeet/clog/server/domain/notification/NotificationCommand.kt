package org.depromeet.clog.server.domain.notification

data class NotificationCommand(
    val userId: Long,
    val type: NotificationType,
    val targetId: Long,
    val title: String,
    val message: String,
)
