package org.depromeet.clog.server.domain.user.domain

interface UserRepository {
    fun findByLoginIdAndProvider(loginId: String, provider: Provider): User?
    fun findByLoginId(loginId: String): User?
    fun findById(id: Long): User?
    fun save(user: User): User
}
