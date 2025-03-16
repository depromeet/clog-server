package org.depromeet.clog.server.domain.crag.domain

import org.depromeet.clog.server.domain.common.ClogException
import org.depromeet.clog.server.domain.common.ErrorCode

class CragNotFoundException(
    message: String? = "암장을 찾을 수 없습니다.",
) : ClogException(
    errorCode = ErrorCode.CRAG_NOT_FOUND,
    message = message,
)
