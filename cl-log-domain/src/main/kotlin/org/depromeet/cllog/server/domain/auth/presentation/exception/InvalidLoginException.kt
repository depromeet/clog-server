package org.depromeet.cllog.server.domain.auth.presentation.exception

import org.depromeet.cllog.server.domain.common.ClogException

class InvalidLoginException : ClogException(AuthErrorCode.INVALID_LOGIN)
