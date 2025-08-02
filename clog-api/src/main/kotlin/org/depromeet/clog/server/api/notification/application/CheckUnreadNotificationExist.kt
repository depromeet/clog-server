package org.depromeet.clog.server.api.notification.application

import org.depromeet.clog.server.domain.notification.NotificationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CheckUnreadNotificationExist(
    private val notificationRepository: NotificationRepository
) {
    operator fun invoke(userId: Long): Boolean {
        return notificationRepository.existsUnreadByUserId(userId)
    }
}
