package org.depromeet.clog.server.domain.user.domain.exception

import org.depromeet.clog.server.domain.common.ClogException
import org.depromeet.clog.server.domain.common.ErrorCode

class UserNotFoundException(
    message: String = "유저를 찾지 못했습니다.",
) : ClogException(
    errorCode = ErrorCode.USER_NOT_FOUND,
    message = message,
)
