package org.depromeet.clog.server.infrastructure.mappers

import org.depromeet.clog.server.domain.user.domain.OtherUser
import org.depromeet.clog.server.infrastructure.user.UserEntity
import org.springframework.stereotype.Component

@Component
class OtherUserMapper {

    fun toDomain(
        entity: UserEntity,
        followings: Set<UserEntity>,
    ): OtherUser {
        return OtherUser(
            id = entity.id!!,
            name = entity.name,
            height = entity.height,
            armSpan = entity.armSpan,
            instagramUrl = entity.instagramUrl,
            isFollowing = followings.contains(entity)
        )
    }
}
