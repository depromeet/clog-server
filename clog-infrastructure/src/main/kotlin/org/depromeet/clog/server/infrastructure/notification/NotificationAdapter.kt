package org.depromeet.clog.server.infrastructure.notification

import org.depromeet.clog.server.domain.notification.NotificationQuery
import org.depromeet.clog.server.domain.notification.NotificationRepository
import org.depromeet.clog.server.infrastructure.mappers.NotificationMapper
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class NotificationAdapter(
    private val notificationJpaRepository: NotificationJpaRepository,
    private val notificationMapper: NotificationMapper
) : NotificationRepository {

    override fun findRecentByUserId(
        userId: Long,
        from: LocalDateTime,
        page: Int,
        size: Int
    ): List<NotificationQuery> {
        val pageable = PageRequest.of(page, size)
        return notificationJpaRepository.findByReceiverIdAndCreatedAtAfterOrderByCreatedAtDesc(
            userId,
            from,
            pageable
        )
            .map { notificationMapper.toDomain(it) }
    }

    override fun clearNewFlags(userId: Long) {
        notificationJpaRepository.clearNewFlags(userId)
    }
}
