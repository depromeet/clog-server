package org.depromeet.clog.server.api.notification.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.depromeet.clog.server.api.configuration.ApiConstants
import org.depromeet.clog.server.api.notification.application.DeleteNotification
import org.depromeet.clog.server.api.notification.application.MarkNotificationRead
import org.depromeet.clog.server.api.user.UserContext
import org.depromeet.clog.server.domain.common.ClogApiResponse
import org.springframework.web.bind.annotation.*

@Tag(name = "Notification API", description = "알림 관련 API")
@RequestMapping("${ApiConstants.API_BASE_PATH_V1}/notifications")
@RestController
class NotificationCommandController(
    private val deleteNotification: DeleteNotification,
    private val markNotificationAsRead: MarkNotificationRead
) {

    @Operation(summary = "알림 삭제", description = "특정 알림을 삭제합니다.")
    @DeleteMapping("/{id}")
    fun deleteNotification(
        userContext: UserContext,
        @PathVariable id: Long,
    ): ClogApiResponse<Unit> {
        deleteNotification(userContext.userId, id)
        return ClogApiResponse.from(Unit)
    }

    @Operation(
        summary = "알림 읽음 처리",
        description = "알림탭에서 알림 클릭으로 직접 이동 시 호출"
    )
    @PatchMapping("/{id}/read")
    fun markAsRead(
        userContext: UserContext,
        @PathVariable id: Long
    ): ClogApiResponse<Unit> {
        markNotificationAsRead(userContext.userId, id)
        return ClogApiResponse.from(Unit)
    }
}
