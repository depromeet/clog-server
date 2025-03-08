package org.depromeet.clog.server.domain.user.domain

interface UserRepository {

    fun save(user: User): User
    fun findByLoginIdAndProviderAndIsDeletedFalse(loginId: String, provider: Provider): User?
    fun findByIdAndIsDeletedFalse(id: Long): User?
}
