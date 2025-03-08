package org.depromeet.clog.server.api.security.jwt

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders

object JwtUtils {

    fun extractToken(request: HttpServletRequest): String? {
        return request.getHeader(HttpHeaders.AUTHORIZATION)
            ?.removePrefix("Bearer ")
            ?.takeIf { it.isNotBlank() }
    }
}
