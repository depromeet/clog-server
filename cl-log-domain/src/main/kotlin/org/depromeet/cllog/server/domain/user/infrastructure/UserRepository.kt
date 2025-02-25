package org.depromeet.cllog.server.domain.user.infrastructure

import org.depromeet.cllog.server.domain.user.domain.Provider
import org.depromeet.cllog.server.domain.user.domain.User
import java.util.*

interface UserRepository {
    fun findByLoginIdAndProvider(loginId: String, provider: Provider): Optional<User>
    fun findByLoginId(loginId: String): Optional<User>
    fun save(user: User): User
}
