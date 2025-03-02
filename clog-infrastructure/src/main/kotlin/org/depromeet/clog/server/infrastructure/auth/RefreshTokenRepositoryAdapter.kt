package org.depromeet.clog.server.infrastructure.auth

import org.depromeet.clog.server.domain.auth.domain.RefreshToken
import org.depromeet.clog.server.domain.auth.infrastructure.RefreshTokenRepository
import org.depromeet.clog.server.domain.user.domain.Provider
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class RefreshTokenRepositoryAdapter(
    private val refreshTokenJpaRepository: RefreshTokenJpaRepository
) : RefreshTokenRepository {

    override fun save(token: RefreshToken): RefreshToken {
        val entity = RefreshTokenEntity.fromDomain(token)
        return refreshTokenJpaRepository.save(entity).toDomain()
    }

    override fun findById(id: String): RefreshToken? =
        refreshTokenJpaRepository.findByIdOrNull(id)?.toDomain()

    override fun deleteByUserIdAndProvider(userId: Long, provider: Provider) =
        refreshTokenJpaRepository.deleteByUserIdAndProvider(userId, provider)
}
