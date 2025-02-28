package org.depromeet.clog.server.infrastructure.auth

import org.depromeet.clog.server.domain.auth.domain.RefreshToken
import org.depromeet.clog.server.domain.auth.infrastructure.RefreshTokenRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class RefreshTokenRepositoryAdapter(
    private val refreshTokenJpaRepository: RefreshTokenJpaRepository
) : RefreshTokenRepository {

    override fun save(token: RefreshToken): RefreshToken =
        refreshTokenJpaRepository.save(token)

    override fun findById(id: String): RefreshToken? =
        refreshTokenJpaRepository.findByIdOrNull(id)

    override fun delete(token: RefreshToken) =
        refreshTokenJpaRepository.delete(token)
}
