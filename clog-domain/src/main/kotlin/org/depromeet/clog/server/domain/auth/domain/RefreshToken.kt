package org.depromeet.clog.server.domain.auth.domain

import org.depromeet.clog.server.domain.user.domain.Provider

data class RefreshToken(
    val userId: Long,
    val loginId: String,
    val provider: Provider,
    var token: String
)
