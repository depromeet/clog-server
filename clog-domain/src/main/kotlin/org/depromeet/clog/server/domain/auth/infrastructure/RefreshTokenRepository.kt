package org.depromeet.clog.server.domain.auth.infrastructure

import org.depromeet.clog.server.domain.auth.domain.RefreshToken

interface RefreshTokenRepository {
    fun save(token: RefreshToken): RefreshToken
    fun findById(id: String): RefreshToken?
    fun delete(token: RefreshToken)
}
