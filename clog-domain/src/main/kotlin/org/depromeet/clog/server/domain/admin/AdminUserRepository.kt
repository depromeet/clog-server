package org.depromeet.clog.server.domain.admin

interface AdminUserRepository {

    fun findByLoginId(loginId: String): AdminUser
}
