package org.depromeet.clog.server.domain.problem

import org.depromeet.clog.server.domain.common.ClogException
import org.depromeet.clog.server.domain.common.ErrorCode

class ProblemNotFoundException(
    message: String = "문제를 찾을 수 없습니다.",
) : ClogException(
    errorCode = ErrorCode.PROBLEM_NOT_FOUND,
    message = message,
)
