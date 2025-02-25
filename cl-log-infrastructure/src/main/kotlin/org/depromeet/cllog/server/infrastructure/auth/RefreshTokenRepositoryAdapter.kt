package org.depromeet.cllog.server.infrastructure.auth

import org.depromeet.cllog.server.domain.auth.domain.RefreshToken
import org.depromeet.cllog.server.domain.auth.infrastructure.RefreshTokenRepository
import org.springframework.stereotype.Component

@Component
class RefreshTokenRepositoryAdapter(
    private val jpaRefreshTokenRepository: JpaRefreshTokenRepository
) : RefreshTokenRepository {

    override fun save(token: RefreshToken): RefreshToken =
        jpaRefreshTokenRepository.save(token)

    override fun findById(id: String): RefreshToken? =
        jpaRefreshTokenRepository.findById(id).orElse(null)

    override fun delete(token: RefreshToken) =
        jpaRefreshTokenRepository.delete(token)
}
