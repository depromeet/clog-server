package org.depromeet.cllog.server.domain.auth.presentation.exception

import org.depromeet.cllog.server.domain.common.ErrorCode

enum class AuthErrorCode(
    override val code: String,
    override val message: String,
    override val httpStatus: Int
) : ErrorCode {
    INVALID_LOGIN("AUTH-001", "잘못된 로그인 요청입니다.", 400),
    USER_NOT_FOUND("AUTH-002", "존재하지 않는 회원입니다.", 404),
    TOKEN_NOT_FOUND("AUTH-002", "토큰이 없습니다.", 404)
}
