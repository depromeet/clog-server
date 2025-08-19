package org.depromeet.clog.server.api.notification.application

import org.depromeet.clog.server.domain.notification.NotificationNotFoundException
import org.depromeet.clog.server.domain.notification.NotificationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DeleteNotification(
    private val notificationRepository: NotificationRepository
) {
    operator fun invoke(userId: Long, notificationId: Long) {
        val deletedCount = notificationRepository.deleteByIdAndUserId(notificationId, userId)
        if (deletedCount == 0) {
            throw NotificationNotFoundException()
        }
    }
}
