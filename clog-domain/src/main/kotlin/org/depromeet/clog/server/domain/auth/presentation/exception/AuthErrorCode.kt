package org.depromeet.clog.server.domain.auth.presentation.exception

import org.depromeet.clog.server.domain.common.ErrorCode

enum class AuthErrorCode(
    override val code: String,
    override val message: String,
    override val httpStatus: Int
) : ErrorCode {

    ID_TOKEN_MISSING("A4000", "id_token이 누락되었습니다.", 400),
    ID_TOKEN_VALIDATION_FAILED("A4001", "id_token 검증에 실패하였습니다.", 400),

    TOKEN_INVALID("A4010", "토큰이 유효하지 않습니다.", 401),
    TOKEN_EXPIRED("A4011", "토큰이 만료되었습니다.", 401),
    AUTHENTICATION_FAILED("A4012", "인증에 실패하였습니다.", 401),
}
