package org.depromeet.clog.server.domain.user.domain

interface UserRepository {

    fun save(user: User): User
    fun findByLoginIdAndProviderAndIsDeletedFalse(loginId: String, provider: Provider): User?
    fun findByLoginIdAndProvider(loginId: String, provider: Provider): User?
    fun findByIdAndIsDeletedFalse(id: Long): User?

    fun findAllActiveUsers(): List<User>

    fun findAllActiveUsersByQuery(
        requestedUserId: Long,
        query: UserQuery,
    ): List<OtherUser>

    fun addFollowing(
        requestedUserId: Long,
        targetUserId: Long,
    )

    fun deleteFollowing(
        requestedUserId: Long,
        targetUserId: Long,
    )
}
