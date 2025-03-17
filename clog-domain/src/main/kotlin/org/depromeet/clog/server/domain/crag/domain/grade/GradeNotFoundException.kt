package org.depromeet.clog.server.domain.crag.domain.grade

import org.depromeet.clog.server.domain.common.ClogException
import org.depromeet.clog.server.domain.common.ErrorCode

class GradeNotFoundException(
    message: String = "등급을 찾을 수 없습니다.",
) : ClogException(
    errorCode = ErrorCode.GRADE_NOT_FOUND,
    message = message
)
