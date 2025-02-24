package org.depromeet.cllog.server.domain.auth.application.dto

import org.depromeet.cllog.server.domain.user.domain.Provider

class LoginDetails(
    val loginId: String,
    val provider: Provider
)
