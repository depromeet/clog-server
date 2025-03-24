package org.depromeet.clog.server.infrastructure.admin

import org.depromeet.clog.server.domain.admin.AdminUser
import org.depromeet.clog.server.domain.admin.AdminUserRepository
import org.depromeet.clog.server.infrastructure.mappers.DomainEntityMapper
import org.springframework.stereotype.Component

@Component
class AdminUserAdapter(
    private val adminUserMapper: DomainEntityMapper<AdminUser, AdminUser, AdminUserEntity>,
    private val adminUserJpaRepository: AdminUserJpaRepository
) : AdminUserRepository {

    override fun findByLoginId(loginId: String): AdminUser {
        val entity = adminUserJpaRepository.findByLoginId(loginId)
        return adminUserMapper.toDomain(entity)
    }
}
