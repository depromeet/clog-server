package org.depromeet.clog.server.domain.admin

data class AdminUser(
    val id: Long? = null,
    val loginId: String,
    val password: String
)
