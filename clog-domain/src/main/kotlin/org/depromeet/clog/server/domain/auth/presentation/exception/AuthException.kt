package org.depromeet.clog.server.domain.auth.presentation.exception

import org.depromeet.clog.server.domain.common.ClogException

class AuthException(
    errorCode: AuthErrorCode,
    cause: Throwable? = null
) : ClogException(
    errorCode = errorCode,
    message = cause.toString(),
)
