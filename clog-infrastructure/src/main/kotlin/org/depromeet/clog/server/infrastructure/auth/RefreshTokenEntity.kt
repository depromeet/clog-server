package org.depromeet.clog.server.infrastructure.auth

import jakarta.persistence.*
import org.depromeet.clog.server.domain.auth.domain.RefreshToken
import org.depromeet.clog.server.domain.user.domain.Provider

@Entity
@Table(name = "refresh_token")
class RefreshTokenEntity(
    @Id
    val userId: Long,

    val loginId: String,

    @Enumerated(EnumType.STRING)
    val provider: Provider,

    @Lob
    @Column(columnDefinition = "TEXT")
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
