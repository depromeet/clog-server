package org.depromeet.clog.server.domain.attempt

import org.depromeet.clog.server.domain.common.ClogException
import org.depromeet.clog.server.domain.common.ErrorCode

class AttemptNotFoundException(
    message: String = "시도를 찾을 수 없습니다.",
) : ClogException(
    errorCode = ErrorCode.ATTEMPT_NOT_FOUND,
    message = message,
)
