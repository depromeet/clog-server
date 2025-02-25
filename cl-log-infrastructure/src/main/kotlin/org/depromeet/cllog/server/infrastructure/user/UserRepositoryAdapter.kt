package org.depromeet.cllog.server.infrastructure.user

import org.depromeet.cllog.server.domain.user.domain.Provider
import org.depromeet.cllog.server.domain.user.domain.User
import org.depromeet.cllog.server.domain.user.infrastructure.UserRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserRepositoryAdapter(
    private val jpaUserRepository: JpaUserRepository
) : UserRepository {

    override fun findByLoginIdAndProvider(loginId: String, provider: Provider): Optional<User> =
        jpaUserRepository.findByLoginIdAndProvider(loginId, provider)

    override fun findByLoginId(loginId: String): Optional<User> =
        jpaUserRepository.findByLoginId(loginId)

    override fun save(user: User): User =
        jpaUserRepository.save(user)
}
