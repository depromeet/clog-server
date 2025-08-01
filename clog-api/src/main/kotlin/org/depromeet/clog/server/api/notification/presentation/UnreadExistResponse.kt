package org.depromeet.clog.server.api.notification.presentation

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "알림 레드닷 표시")
data class UnreadExistResponse(
    @Schema(description = "true: 새 알림 존재 / false: 새 알림 없음", example = "true")
    val exists: Boolean
)
