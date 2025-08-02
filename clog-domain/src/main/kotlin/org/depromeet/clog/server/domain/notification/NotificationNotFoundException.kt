package org.depromeet.clog.server.domain.notification

import org.depromeet.clog.server.domain.common.ClogException
import org.depromeet.clog.server.domain.common.ErrorCode

class NotificationNotFoundException(
    message: String = "해당 알림을 찾을 수 없습니다.",
) : ClogException(
    errorCode = ErrorCode.NOTIFICATION_NOT_FOUND,
    message = message,
)
