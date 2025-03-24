package org.depromeet.clog.server.infrastructure.mappers

import org.depromeet.clog.server.domain.admin.AdminUser
import org.depromeet.clog.server.infrastructure.admin.AdminUserEntity
import org.springframework.stereotype.Component

@Component
class AdminUserMapper : DomainEntityMapper<AdminUser, AdminUser, AdminUserEntity> {

    override fun toDomain(entity: AdminUserEntity): AdminUser {
        return AdminUser(
            id = entity.id,
            loginId = entity.loginId,
            password = entity.password
        )
    }

    override fun toEntity(domain: AdminUser): AdminUserEntity {
        return AdminUserEntity(
            id = domain.id,
            loginId = domain.loginId,
            password = domain.password
        )
    }
}
