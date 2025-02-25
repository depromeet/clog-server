package org.depromeet.cllog.server.infrastructure.user

import org.depromeet.cllog.server.domain.user.domain.Provider
import org.depromeet.cllog.server.domain.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface JpaUserRepository : JpaRepository<User, Long> {
    fun findByLoginIdAndProvider(loginId: String, provider: Provider): Optional<User>
    fun findByLoginId(loginId: String): Optional<User>
}
