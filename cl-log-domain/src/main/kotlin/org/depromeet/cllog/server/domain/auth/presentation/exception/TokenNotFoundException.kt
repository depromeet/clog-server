package org.depromeet.cllog.server.domain.auth.presentation.exception

import org.depromeet.cllog.server.domain.common.ClogException

class TokenNotFoundException : ClogException(AuthErrorCode.TOKEN_NOT_FOUND)
