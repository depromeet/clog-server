package org.depromeet.clog.server.api.security.jwt

import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.depromeet.clog.server.domain.auth.application.TokenService
import org.depromeet.clog.server.domain.auth.presentation.exception.AuthException
import org.depromeet.clog.server.domain.user.infrastructure.UserRepository
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class JwtFilter(
    private val tokenService: TokenService,
    private val userRepository: UserRepository
) : OncePerRequestFilter() {

    private val log = KotlinLogging.logger {}

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val rawToken = extractToken(request)
        if (rawToken == null) {
            filterChain.doFilter(request, response)
            return
        }

        try {
            val loginDetails = tokenService.extractLoginDetails(rawToken)
            val user = userRepository.findByLoginIdAndProvider(loginDetails.loginId, loginDetails.provider)

            if (user == null) {
                log.warn("사용자 없음: ${loginDetails.loginId}")
                filterChain.doFilter(request, response)
                return
            }
            if (loginDetails.provider == user.provider) {
                val authentication = UsernamePasswordAuthenticationToken(user, null, emptyList())
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (e: AuthException) {
            log.error(e) { "Auth exception 발생: ${e.message}" }
        } catch (e: TokenExpiredException) {
            log.error(e) { "JWT Token expired: ${e.message}" }
        } catch (e: JWTVerificationException) {
            log.error(e) { "JWT Verification failed: ${e.message}" }
        }

        filterChain.doFilter(request, response)
    }

    private fun extractToken(request: HttpServletRequest): String? {
        return request.getHeader(HttpHeaders.AUTHORIZATION)
            ?.removePrefix("Bearer ")
            ?.takeIf { it.isNotBlank() }
    }
}
