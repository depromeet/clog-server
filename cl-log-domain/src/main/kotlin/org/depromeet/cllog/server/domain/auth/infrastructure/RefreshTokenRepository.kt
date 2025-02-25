package org.depromeet.cllog.server.domain.auth.infrastructure

import org.depromeet.cllog.server.domain.auth.domain.RefreshToken

interface RefreshTokenRepository {
    fun save(token: RefreshToken): RefreshToken
    fun findById(id: String): RefreshToken?
    fun delete(token: RefreshToken)
}
