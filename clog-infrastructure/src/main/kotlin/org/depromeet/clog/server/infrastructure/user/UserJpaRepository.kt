package org.depromeet.clog.server.infrastructure.user

import org.depromeet.clog.server.domain.user.domain.Provider
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserEntity, Long> {
    fun findByLoginIdAndProviderAndIsDeletedFalse(loginId: String, provider: Provider): UserEntity?
    fun findByIdAndIsDeletedFalse(id: Long): UserEntity?
    fun findByLoginIdAndProvider(loginId: String, provider: Provider): UserEntity?
    fun findAllByIsDeletedFalse(): List<UserEntity>
}
