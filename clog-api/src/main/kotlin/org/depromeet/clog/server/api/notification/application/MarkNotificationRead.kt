package org.depromeet.clog.server.api.notification.application

import org.depromeet.clog.server.domain.notification.NotificationNotFoundException
import org.depromeet.clog.server.domain.notification.NotificationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MarkNotificationRead(
    private val notificationRepository: NotificationRepository
) {
    operator fun invoke(userId: Long, notificationId: Long) {
        val affected = notificationRepository.markAsRead(userId, notificationId)
        if (affected == 0) {
            throw NotificationNotFoundException()
        }
    }
}
