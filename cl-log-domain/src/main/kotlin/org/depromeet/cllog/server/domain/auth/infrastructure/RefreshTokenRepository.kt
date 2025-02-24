package org.depromeet.cllog.server.domain.auth.infrastructure

import org.depromeet.cllog.server.domain.auth.domain.RefreshToken
import org.depromeet.cllog.server.domain.user.domain.Provider
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface RefreshTokenRepository : JpaRepository<RefreshToken, String> {
    fun findByEmailAndProvider(email: String, provider: Provider): Optional<RefreshToken>
}
