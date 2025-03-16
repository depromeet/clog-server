package org.depromeet.clog.server.infrastructure.mappers

import org.depromeet.clog.server.domain.user.domain.User
import org.depromeet.clog.server.infrastructure.user.UserEntity
import org.springframework.stereotype.Component

@Component
class UserMapper {

    fun toDomain(entity: UserEntity): User {
        return User(
            id = entity.id,
            loginId = entity.loginId,
            name = entity.name,
            provider = entity.provider,
            isDeleted = entity.isDeleted,
        )
    }

    fun toEntity(domain: User): UserEntity {
        return UserEntity(
            id = domain.id,
            loginId = domain.loginId,
            name = domain.name,
            provider = domain.provider,
            isDeleted = domain.isDeleted,
        )
    }
}
