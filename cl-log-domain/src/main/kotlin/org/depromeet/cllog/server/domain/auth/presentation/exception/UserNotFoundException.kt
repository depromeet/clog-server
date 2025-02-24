package org.depromeet.cllog.server.domain.auth.presentation.exception

import org.depromeet.cllog.server.domain.common.ClogException

class UserNotFoundException : ClogException(AuthErrorCode.USER_NOT_FOUND)
