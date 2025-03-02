package org.depromeet.clog.server.domain.example

import org.depromeet.clog.server.domain.common.ClogException
import org.depromeet.clog.server.domain.common.ErrorCode
import org.springframework.context.annotation.Profile

@Deprecated("예시용으로, 곧 삭제될 예정입니다. (25.02.19 / kkjsw17)")
@Profile("local")
class ExampleException : ClogException(ErrorCode.EXAMPLE_ERROR_CODE)
