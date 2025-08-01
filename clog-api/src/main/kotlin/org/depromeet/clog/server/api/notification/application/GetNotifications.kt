package org.depromeet.clog.server.api.notification.application

import org.depromeet.clog.server.api.notification.presentation.NotificationResponse
import org.depromeet.clog.server.domain.notification.NotificationRepository
import org.depromeet.clog.server.domain.notification.NotificationType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class GetNotifications(
    private val notificationRepository: NotificationRepository,
) {
    operator fun invoke(
        userId: Long,
        page: Int,
        size: Int,
        type: NotificationType?
    ): List<NotificationResponse> {
        val thresholdDate = LocalDateTime.now().minusDays(30)
        notificationRepository.clearNewFlags(userId)

        val notifications = if (type != null) {
            notificationRepository.findByUserIdAndType(userId, type, thresholdDate, page, size)
        } else {
            notificationRepository.findRecentByUserId(userId, thresholdDate, page, size)
        }

        return notifications.map { NotificationResponse.from(it) }
    }
}
