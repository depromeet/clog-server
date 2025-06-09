package org.depromeet.clog.server.infrastructure.user

import org.depromeet.clog.server.domain.user.domain.*
import org.depromeet.clog.server.infrastructure.mappers.OtherUserMapper
import org.depromeet.clog.server.infrastructure.mappers.UserMapper
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class UserAdapter(
    private val userJpaRepository: UserJpaRepository,
    private val userMapper: UserMapper,
    private val otherUserMapper: OtherUserMapper,
) : UserRepository {

    override fun findByLoginIdAndProviderAndIsDeletedFalse(
        loginId: String,
        provider: Provider
    ): User? = userJpaRepository.findByLoginIdAndProviderAndIsDeletedFalse(
        loginId = loginId,
        provider = provider
    )?.let { userMapper.toDomain(it) }

    override fun findByLoginIdAndProvider(
        loginId: String,
        provider: Provider
    ): User? =
        userJpaRepository.findByLoginIdAndProvider(
            loginId = loginId,
            provider = provider
        )?.let { userMapper.toDomain(it) }

    override fun findByIdAndIsDeletedFalse(id: Long): User? =
        userJpaRepository.findByIdAndIsDeletedFalse(id)?.let {
            userMapper.toDomain(it)
        }

    override fun findAllActiveUsersByQuery(
        requestedUserId: Long,
        query: UserQuery,
    ): List<OtherUser> {
        val requestedUser = (
            userJpaRepository.findByIdOrNull(requestedUserId)
                ?: throw IllegalArgumentException("User with id $requestedUserId not found")
            )

        return userJpaRepository.findPage(PageRequest.of(0, query.pageSize + 1)) {
            val conditions = listOfNotNull(
                query.cursor?.let { path(UserEntity::id).lessThan(it) },
                query.keyword?.let { path(UserEntity::name).like("%$it%") },
                path(UserEntity::isDeleted).eq(false),
                path(UserEntity::id).ne(requestedUserId),
            )

            select(
                entity(UserEntity::class)
            ).from(
                entity(UserEntity::class)
            ).where(
                and(
                    *conditions.toTypedArray()
                )
            ).orderBy(
                path(UserEntity::id).desc()
            )
        }
            .filterNotNull()
            .map { otherUserMapper.toDomain(it, requestedUser.followings) }
    }

    override fun deleteFollowing(requestedUserId: Long, targetUserId: Long) {
        val requestedUser = userJpaRepository.findByIdOrNull(requestedUserId)
            ?: throw IllegalArgumentException("User with id $requestedUserId not found")
        val targetUser = userJpaRepository.findByIdOrNull(targetUserId)
            ?: throw IllegalArgumentException("Target user with id $targetUserId not found")

        requestedUser.followings.remove(targetUser)
        userJpaRepository.save(requestedUser)
    }

    override fun save(user: User): User {
        val entity = userMapper.toEntity(user)
        return userJpaRepository.save(entity)
            .let { userMapper.toDomain(it) }
    }

    override fun findAllActiveUsers(): List<User> =
        userJpaRepository.findAllByIsDeletedFalse().map { userMapper.toDomain(it) }
}
