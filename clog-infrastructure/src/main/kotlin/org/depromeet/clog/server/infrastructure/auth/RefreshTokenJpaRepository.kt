package org.depromeet.clog.server.infrastructure.auth

import org.depromeet.clog.server.domain.auth.domain.RefreshToken
import org.depromeet.clog.server.domain.user.domain.Provider
import org.springframework.data.jpa.repository.JpaRepository

interface RefreshTokenJpaRepository : JpaRepository<RefreshToken, String>{
    fun deleteByLoginIdAndProvider(loginId: String, provider: Provider)
}
