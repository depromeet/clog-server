package org.depromeet.clog.server.infrastructure.auth

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import org.depromeet.clog.server.domain.auth.domain.RefreshToken
import org.depromeet.clog.server.domain.user.domain.Provider

@Entity
class RefreshTokenEntity(
    @Id
    val userId: Long,

    val loginId: String,

    @Enumerated(EnumType.STRING)
    val provider: Provider,

    var token: String
) {
    fun toDomain(): RefreshToken = RefreshToken(
        userId = userId,
        loginId = loginId,
        provider = provider,
        token = token
    )

    companion object {
        fun fromDomain(refreshToken: RefreshToken): RefreshTokenEntity =
            RefreshTokenEntity(
                userId = refreshToken.userId,
                loginId = refreshToken.loginId,
                provider = refreshToken.provider,
                token = refreshToken.token
            )
    }
}
