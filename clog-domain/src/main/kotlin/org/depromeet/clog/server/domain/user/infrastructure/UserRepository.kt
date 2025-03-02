package org.depromeet.clog.server.domain.user.infrastructure

import org.depromeet.clog.server.domain.user.domain.Provider
import org.depromeet.clog.server.domain.user.domain.User

interface UserRepository {
    fun findByLoginIdAndProvider(loginId: String, provider: Provider): User?
    fun findByLoginId(loginId: String): User?
    fun save(user: User): User
}
