package org.depromeet.clog.server.infrastructure.user

import org.depromeet.clog.server.domain.user.domain.Provider
import org.depromeet.clog.server.domain.user.domain.User
import org.depromeet.clog.server.domain.user.infrastructure.UserRepository
import org.springframework.stereotype.Component

@Component
class UserRepositoryAdapter(
    private val jpaUserRepository: JpaUserRepository
) : UserRepository {

    override fun findByLoginIdAndProvider(loginId: String, provider: Provider): User? =
        jpaUserRepository.findByLoginIdAndProvider(loginId, provider).orElse(null)

    override fun findByLoginId(loginId: String): User? =
        jpaUserRepository.findByLoginId(loginId).orElse(null)

    override fun save(user: User): User =
        jpaUserRepository.save(user)
}
