package org.depromeet.clog.server.infrastructure.user

import org.depromeet.clog.server.domain.user.domain.Provider
import org.depromeet.clog.server.domain.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<User, Long> {
    fun findByLoginIdAndProvider(loginId: String, provider: Provider): User?
    fun findByLoginId(loginId: String): User?
}
