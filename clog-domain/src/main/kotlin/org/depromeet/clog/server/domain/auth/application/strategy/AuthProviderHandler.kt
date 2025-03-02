package org.depromeet.clog.server.domain.auth.application.strategy

import org.depromeet.clog.server.domain.auth.application.dto.AuthResponseDto

interface AuthProviderHandler<T> {
    fun login(request: T): AuthResponseDto
}
