package org.depromeet.clog.server.infrastructure.user

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import org.depromeet.clog.server.domain.user.domain.Provider
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserEntity, Long>, KotlinJdslJpqlExecutor {
    fun findByLoginIdAndProviderAndIsDeletedFalse(loginId: String, provider: Provider): UserEntity?
    fun findByIdAndIsDeletedFalse(id: Long): UserEntity?
    fun findByLoginIdAndProvider(loginId: String, provider: Provider): UserEntity?
    fun findAllByIsDeletedFalse(): List<UserEntity>
}
