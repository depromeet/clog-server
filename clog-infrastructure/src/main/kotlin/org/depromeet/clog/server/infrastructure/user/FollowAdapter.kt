package org.depromeet.clog.server.infrastructure.user

import org.depromeet.clog.server.domain.user.domain.FollowRepository
import org.depromeet.clog.server.domain.user.domain.OtherUser
import org.depromeet.clog.server.infrastructure.mappers.OtherUserMapper
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class FollowAdapter(
    private val jpaRepository: UserJpaRepository,
    private val otherUserMapper: OtherUserMapper,
) : FollowRepository {

    override fun findFollowersByUserId(userId: Long): List<OtherUser> {
        val user = jpaRepository.findByIdOrNull(userId)
            ?: throw IllegalArgumentException("User with id $userId not found")

        return user.followers.map { otherUserMapper.toDomain(it, user.followings) }
    }

    override fun findFollowingByUserId(userId: Long): List<OtherUser> {
        val user = jpaRepository.findByIdOrNull(userId)
            ?: throw IllegalArgumentException("User with id $userId not found")

        return user.followings.map { otherUserMapper.toDomain(it, user.followings) }
    }
}
