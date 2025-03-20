package org.depromeet.clog.server.domain.user.presentation.exception

import org.depromeet.clog.server.domain.common.ClogException
import org.depromeet.clog.server.domain.common.ErrorCode

class AppleRevokeException(
    message: String = "애플 계정 탈퇴 처리 중 revoke 요청 실패",
    cause: Throwable? = null
) : ClogException(
    errorCode = ErrorCode.APPLE_REVOKE_REQUEST_FAILED,
    message = message
) {
    init {
        if (cause != null) {
            initCause(cause)
        }
    }
}
