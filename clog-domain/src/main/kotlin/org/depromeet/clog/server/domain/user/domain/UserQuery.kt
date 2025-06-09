package org.depromeet.clog.server.domain.user.domain

import org.springdoc.core.annotations.ParameterObject

@ParameterObject
data class UserQuery(
    val cursor: Long? = null,
    val pageSize: Int = 10,
    val keyword: String? = null,
)
