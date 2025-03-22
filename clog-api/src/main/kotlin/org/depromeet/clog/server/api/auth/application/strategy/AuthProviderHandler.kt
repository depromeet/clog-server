package org.depromeet.clog.server.api.auth.application.strategy

import org.depromeet.clog.server.api.auth.application.dto.response.AuthResponseDto

interface AuthProviderHandler<T> {
    fun login(request: T): AuthResponseDto
}
