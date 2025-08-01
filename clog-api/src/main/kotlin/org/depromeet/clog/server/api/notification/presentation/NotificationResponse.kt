package org.depromeet.clog.server.api.notification.presentation

import io.swagger.v3.oas.annotations.media.Schema
import org.depromeet.clog.server.domain.notification.NotificationQuery
import java.time.LocalDateTime

@Schema(description = "알림 응답")
data class NotificationResponse(
    @Schema(description = "알림 ID") val id: Long,
    @Schema(description = "알림 타입(FOLLOW / EVENT)") val type: String,
    @Schema(description = "대상 리디렉션 ID") val targetId: Long,
    @Schema(description = "제목") val title: String,
    @Schema(description = "메시지") val message: String,
    @Schema(description = "읽음 여부") val isRead: Boolean,
    @Schema(description = "알림 생성 시각") val createdAt: LocalDateTime,
) {
    companion object {
        fun from(notificationQuery: NotificationQuery): NotificationResponse {
            return NotificationResponse(
                id = notificationQuery.id,
                type = notificationQuery.type.name,
                targetId = notificationQuery.targetId,
                title = notificationQuery.title,
                message = notificationQuery.message,
                isRead = notificationQuery.isRead,
                createdAt = notificationQuery.createdAt
            )
        }
    }
}
