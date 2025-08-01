package org.depromeet.clog.server.api.notification.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.tags.Tag
import org.depromeet.clog.server.api.configuration.ApiConstants
import org.depromeet.clog.server.api.notification.application.GetNotifications
import org.depromeet.clog.server.api.user.UserContext
import org.depromeet.clog.server.domain.common.ClogApiResponse
import org.depromeet.clog.server.domain.notification.NotificationType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Notification API", description = "알림 조회 API")
@RequestMapping("${ApiConstants.API_BASE_PATH_V1}/notifications")
@RestController
class NotificationQueryController(
    private val getNotifications: GetNotifications,
) {

    @Operation(summary = "알림 전체 조회", description = "최근 30일 이내의 알림을 최신순으로 조회.")
    @GetMapping
    fun getNotifications(
        userContext: UserContext,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @Parameter(
            description = "알림 타입 필터 (FOLLOW : 친구추가 / EVENT : 이벤트 / null : 전체)",
            `in` = ParameterIn.QUERY
        )
        @RequestParam(required = false) type: NotificationType? = null
    ): ClogApiResponse<List<NotificationResponse>> {
        val notifications = getNotifications(userContext.userId, page, size, type)
        return ClogApiResponse.from(notifications)
    }
}
