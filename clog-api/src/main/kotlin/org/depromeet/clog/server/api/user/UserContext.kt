package org.depromeet.clog.server.api.user

import io.swagger.v3.oas.annotations.Hidden

@Hidden
data class UserContext(
    val userId: Long,
)
