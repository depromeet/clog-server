package org.depromeet.cllog.server.infrastructure.auth

import org.depromeet.cllog.server.domain.auth.domain.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface JpaRefreshTokenRepository : JpaRepository<RefreshToken, String>
