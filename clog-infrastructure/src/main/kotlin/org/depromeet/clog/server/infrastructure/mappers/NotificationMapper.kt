package org.depromeet.clog.server.infrastructure.mappers

import org.depromeet.clog.server.domain.notification.NotificationQuery
import org.depromeet.clog.server.infrastructure.notification.NotificationEntity
import org.springframework.stereotype.Component

@Component
class NotificationMapper {

    fun toDomain(entity: NotificationEntity): NotificationQuery {
        return NotificationQuery(
            id = entity.id!!,
            userId = entity.receiver.id!!,
            type = entity.type,
            title = entity.title,
            message = entity.message,
            targetId = entity.targetId,
            isRead = entity.isRead,
            readAt = entity.readAt,
            isNewFlagCleared = entity.isNewFlagCleared,
            createdAt = entity.createdAt
        )
    }
}
