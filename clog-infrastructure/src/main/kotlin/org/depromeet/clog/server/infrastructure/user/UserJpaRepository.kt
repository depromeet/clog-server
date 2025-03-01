package org.depromeet.clog.server.infrastructure.user

import org.depromeet.clog.server.domain.user.domain.Provider
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserEntity, Long> {
    fun findByLoginIdAndProvider(loginId: String, provider: Provider): UserEntity?
    fun findByLoginId(loginId: String): UserEntity?
}
