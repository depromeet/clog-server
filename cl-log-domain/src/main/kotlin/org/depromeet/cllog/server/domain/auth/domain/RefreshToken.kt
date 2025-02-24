package org.depromeet.cllog.server.domain.auth.domain

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import org.depromeet.cllog.server.domain.user.domain.Provider
import org.depromeet.cllog.server.domain.user.domain.User

@Entity
class RefreshToken(
    @Id
    val email: String,

    @Enumerated(EnumType.STRING)
    val provider: Provider,

    var token: String
) {
    fun updateToken(refreshToken: String) {
        this.token = refreshToken
    }

    fun isSameToken(refreshToken: String): Boolean {
        return this.token == refreshToken
    }

    companion object {
        fun fromUser(user: User, refreshToken: String): RefreshToken {
            return RefreshToken(
                email = user.loginId,
                provider = user.provider,
                token = refreshToken
            )
        }
    }
}
