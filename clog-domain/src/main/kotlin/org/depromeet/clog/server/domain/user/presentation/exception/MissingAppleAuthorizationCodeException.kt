package org.depromeet.clog.server.domain.user.presentation.exception

import org.depromeet.clog.server.domain.common.ClogException
import org.depromeet.clog.server.domain.common.ErrorCode

class MissingAppleAuthorizationCodeException(
    message: String = "애플 소셜 로그인 사용자 탈퇴를 진행하려면, 유효한 authorizationCode가 필요합니다.",
) : ClogException(
    errorCode = ErrorCode.APPLE_AUTHORIZATION_CODE_MISSING,
    message = message,
)
