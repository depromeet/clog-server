package org.depromeet.clog.server.domain.user.domain

data class User(
    val id: Long? = null,
    val loginId: String,
    var name: String? = null,
    val provider: Provider,
    var isDeleted: Boolean = false
)
