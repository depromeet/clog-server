package org.depromeet.clog.server.domain.auth.infrastructure

import org.depromeet.clog.server.domain.auth.domain.RefreshToken
import org.depromeet.clog.server.domain.user.domain.Provider

interface RefreshTokenRepository {
    fun save(token: RefreshToken): RefreshToken
    fun findByUserId(id: Long): RefreshToken?
    fun deleteByUserIdAndProvider(userId: Long, provider: Provider)
}
