package org.depromeet.clog.server.infrastructure.admin

import org.springframework.data.jpa.repository.JpaRepository

interface AdminUserJpaRepository : JpaRepository<AdminUserEntity, Long> {

    fun findByLoginId(loginId: String): AdminUserEntity
}
