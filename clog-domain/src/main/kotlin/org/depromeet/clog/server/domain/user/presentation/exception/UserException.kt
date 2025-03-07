package org.depromeet.clog.server.domain.user.presentation.exception

import org.depromeet.clog.server.domain.common.ClogException
import org.depromeet.clog.server.domain.common.ErrorCode

class UserException(
    errorCode: ErrorCode,
    cause: Throwable? = null
) : ClogException(
    errorCode = errorCode,
    message = cause.toString(),
)
