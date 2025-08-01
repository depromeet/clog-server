package org.depromeet.clog.server.api.notification.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.depromeet.clog.server.api.configuration.ApiConstants
import org.depromeet.clog.server.api.notification.application.DeleteNotification
import org.depromeet.clog.server.api.user.UserContext
import org.depromeet.clog.server.domain.common.ClogApiResponse
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Notification API", description = "알림 관련 API")
@RequestMapping("${ApiConstants.API_BASE_PATH_V1}/notifications")
@RestController
class NotificationCommandController(
    private val deleteNotification: DeleteNotification,
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
}
