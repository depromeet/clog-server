package org.depromeet.clog.server.domain.auth.presentation.exception

import org.depromeet.clog.server.domain.common.ClogException
import org.depromeet.clog.server.domain.common.ErrorCode

class AuthException(
    errorCode: ErrorCode,
    cause: Throwable? = null
) : ClogException(
    errorCode = errorCode,
    message = cause.toString(),
)
