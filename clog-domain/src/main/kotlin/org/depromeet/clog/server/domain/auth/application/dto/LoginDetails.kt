package org.depromeet.clog.server.domain.auth.application.dto

import org.depromeet.clog.server.domain.user.domain.Provider

data class LoginDetails(
    val loginId: String,
    val provider: Provider
)
