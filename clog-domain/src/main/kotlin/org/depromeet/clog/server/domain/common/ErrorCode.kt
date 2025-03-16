package org.depromeet.clog.server.domain.common

enum class ErrorCode(
    val code: String,
    val message: String,
    val httpStatus: Int
) {

    EXAMPLE_ERROR_CODE("C9999", "Example error message", 400),

    ID_TOKEN_MISSING("C4000", "id_token이 누락되었습니다.", 400),
    ID_TOKEN_VALIDATION_FAILED("C4001", "id_token 검증에 실패하였습니다.", 400),

    TOKEN_INVALID("C4010", "토큰이 유효하지 않습니다.", 401),
    TOKEN_EXPIRED("C4011", "토큰이 만료되었습니다.", 401),
    AUTHENTICATION_FAILED("C4012", "인증에 실패하였습니다.", 401),

    FORBIDDEN("C4030", "접근 권한이 없습니다.", 403),

    USER_NOT_FOUND("C4040", "존재하지 않는 회원입니다.", 404),
    VIDEO_NOT_FOUND("C4041", "존재하지 않는 영상입니다.", 404),
    ATTEMPT_NOT_FOUND("C4042", "존재하지 않는 시도입니다.", 404),
    STORY_NOT_FOUND("C4043", "존재하지 않는 기록입니다.", 404),
    CRAG_NOT_FOUND("C4044", "존재하지 않는 암장입니다.", 404),
    PROBLEM_NOT_FOUND("C4045", "존재하지 않는 문제입니다.", 404),
    GRADE_NOT_FOUND("C4046", "존재하지 않는 난이도입니다.", 404),
}
