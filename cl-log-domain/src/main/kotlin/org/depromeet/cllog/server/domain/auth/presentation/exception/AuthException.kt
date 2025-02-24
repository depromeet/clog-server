package org.depromeet.cllog.server.domain.auth.presentation.exception

import org.depromeet.cllog.server.domain.common.ClogException

class AuthException(
    errorCode: AuthErrorCode,
    cause: Throwable? = null
) : ClogException(errorCode, cause.toString())
