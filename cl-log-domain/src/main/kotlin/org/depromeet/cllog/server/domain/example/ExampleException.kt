package org.depromeet.cllog.server.domain.example

import org.depromeet.cllog.server.domain.common.ClogException
import org.depromeet.cllog.server.domain.example.ExampleErrorCode.EXAMPLE_ERROR_CODE
import org.springframework.context.annotation.Profile

@Deprecated("예시용으로, 곧 삭제될 예정입니다. (25.02.19 / kkjsw17)")
@Profile("local")
class ExampleException : ClogException(EXAMPLE_ERROR_CODE)
