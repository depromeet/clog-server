package org.depromeet.clog.server.domain.example

import org.depromeet.clog.server.domain.common.ErrorCode
import org.springframework.context.annotation.Profile

@Deprecated("예시용으로, 곧 삭제될 예정입니다. (25.02.19 / kkjsw17)")
@Profile("local")
enum class ExampleErrorCode(
    override val code: String,
    override val message: String,
    override val httpStatus: Int,
) : ErrorCode {

    EXAMPLE_ERROR_CODE("C4001", "Example error message", 400),
}
