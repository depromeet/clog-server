package org.depromeet.clog.server.domain.auth.domain

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import org.depromeet.clog.server.domain.user.domain.Provider

@Entity
class RefreshToken(
    @Id
    val loginId: String,

    @Enumerated(EnumType.STRING)
    val provider: Provider,

    var token: String
)
