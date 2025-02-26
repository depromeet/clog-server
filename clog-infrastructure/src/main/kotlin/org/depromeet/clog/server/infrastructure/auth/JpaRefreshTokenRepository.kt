package org.depromeet.clog.server.infrastructure.auth

import org.depromeet.clog.server.domain.auth.domain.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository

interface JpaRefreshTokenRepository : JpaRepository<RefreshToken, String>
