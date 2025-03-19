package org.depromeet.clog.server.domain.user.presentation.exception

import org.depromeet.clog.server.domain.common.ClogException
import org.depromeet.clog.server.domain.common.ErrorCode

class UserNotFoundException(
    message: String = "존재하지 않는 회원입니다.",
) : ClogException(
    errorCode = ErrorCode.USER_NOT_FOUND,
    message = message,
)
