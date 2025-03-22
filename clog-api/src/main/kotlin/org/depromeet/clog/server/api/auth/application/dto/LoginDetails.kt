package org.depromeet.clog.server.api.auth.application.dto

import org.depromeet.clog.server.domain.user.domain.Provider

data class LoginDetails(
    val userId: Long,
    val loginId: String,
    val provider: Provider
)
