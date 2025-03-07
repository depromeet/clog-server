package org.depromeet.clog.server.domain.common

enum class ErrorCode(
    val code: String,
    val message: String,
    val httpStatus: Int
) {

    EXAMPLE_ERROR_CODE("C9999", "Example error message", 400),

    // AUTH
    ID_TOKEN_MISSING("C4000", "id_token이 누락되었습니다.", 400),
    ID_TOKEN_VALIDATION_FAILED("C4001", "id_token 검증에 실패하였습니다.", 400),
    TOKEN_INVALID("C4010", "토큰이 유효하지 않습니다.", 401),
    TOKEN_EXPIRED("C4011", "토큰이 만료되었습니다.", 401),
    AUTHENTICATION_FAILED("C4012", "인증에 실패하였습니다.", 401),

    // USER
    USER_NOT_FOUND("C4040", "존재하지 않는 회원입니다.", 404)
}
