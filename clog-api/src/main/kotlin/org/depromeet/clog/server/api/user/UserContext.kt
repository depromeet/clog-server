package org.depromeet.clog.server.api.user

import io.swagger.v3.oas.annotations.Hidden
import org.depromeet.clog.server.domain.common.AppVersion

@Hidden
data class UserContext(
    val userId: Long,
    val appVersion: AppVersion? = null,
)
