package org.depromeet.cllog.server.api.security.jwt

import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.depromeet.cllog.server.domain.auth.application.TokenService
import org.depromeet.cllog.server.domain.auth.presentation.exception.TokenNotFoundException
import org.depromeet.cllog.server.domain.user.infrastructure.UserRepository
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

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val rawToken = request.getHeader(HttpHeaders.AUTHORIZATION)?.removePrefix("Bearer ")

        if (rawToken.isNullOrEmpty()) {
            filterChain.doFilter(request, response)
            return
        }

        try {
            val loginDetails = tokenService.extractLoginDetails(rawToken)
            val user = userRepository.findByLoginIdAndProvider(loginDetails.loginId,loginDetails.provider).orElse(null) ?: return

            if (loginDetails.provider == user.provider) {
                val authentication = UsernamePasswordAuthenticationToken(user, null, emptyList())
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (e: TokenExpiredException) {
            logger.error("JWT Token expired: ${e.message}")
        } catch (e: JWTVerificationException) {
            logger.error("JWT Verification failed: ${e.message}")
        } catch (e: TokenNotFoundException) {
            logger.error("JWT Token not found: ${e.message}")
        }

        filterChain.doFilter(request, response)
    }
}
