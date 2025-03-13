package org.depromeet.clog.server.domain.auth.domain

import org.depromeet.clog.server.domain.common.ClogException
import org.depromeet.clog.server.domain.common.ErrorCode

class ForbiddenException(
    message: String,
) : ClogException(
    errorCode = ErrorCode.FORBIDDEN,
    message = message,
)
