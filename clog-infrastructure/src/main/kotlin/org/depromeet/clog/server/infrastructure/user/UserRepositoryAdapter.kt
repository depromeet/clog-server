package org.depromeet.clog.server.infrastructure.user

import org.depromeet.clog.server.domain.user.domain.Provider
import org.depromeet.clog.server.domain.user.domain.User
import org.depromeet.clog.server.domain.user.infrastructure.UserRepository
import org.springframework.stereotype.Component

@Component
class UserRepositoryAdapter(
    private val userJpaRepository: UserJpaRepository
) : UserRepository {

    override fun findByLoginIdAndProvider(loginId: String, provider: Provider): User? =
        userJpaRepository.findByLoginIdAndProvider(loginId, provider)?.toDomain()

    override fun findByLoginId(loginId: String): User? =
        userJpaRepository.findByLoginId(loginId)?.toDomain()

    override fun save(user: User): User =
        userJpaRepository.save(UserEntity.fromDomain(user)).toDomain()
}
