package org.depromeet.clog.server.infrastructure.user

import org.depromeet.clog.server.domain.user.domain.Provider
import org.depromeet.clog.server.domain.user.domain.User
import org.depromeet.clog.server.domain.user.domain.UserRepository
import org.depromeet.clog.server.infrastructure.mappers.UserMapper
import org.springframework.stereotype.Component

@Component
class UserRepositoryAdapter(
    private val userMapper: UserMapper,
    private val userJpaRepository: UserJpaRepository
) : UserRepository {

    override fun findByLoginIdAndProviderAndIsDeletedFalse(
        loginId: String,
        provider: Provider
    ): User? = userJpaRepository.findByLoginIdAndProviderAndIsDeletedFalse(
        loginId = loginId,
        provider = provider
    )?.let { userMapper.toDomain(it) }

    override fun findByLoginIdAndProvider(
        loginId: String,
        provider: Provider
    ): User? =
        userJpaRepository.findByLoginIdAndProvider(
            loginId = loginId,
            provider = provider
        )?.let { userMapper.toDomain(it) }

    override fun findByIdAndIsDeletedFalse(id: Long): User? =
        userJpaRepository.findByIdAndIsDeletedFalse(id)?.let {
            userMapper.toDomain(it)
        }

    override fun save(user: User): User {
        val entity = userMapper.toEntity(user)
        return userJpaRepository.save(entity)
            .let { userMapper.toDomain(it) }
    }
}
