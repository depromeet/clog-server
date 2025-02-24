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
        val rawToken = request.getHeader(HttpHeaders.AUTHORIZATION)

        try {
            val parsedToken = tokenService.parseAccessToken(rawToken) ?: throw TokenNotFoundException()
            tokenService.verifyToken(parsedToken)
            authenticateUser(parsedToken, request)
        } catch (e: TokenNotFoundException) {
            logger.error("JWT Token not found: ${e.message}")
        } catch (e: TokenExpiredException) {
            logger.error("JWT Token expired: ${e.message}")
        } catch (e: JWTVerificationException) {
            logger.error("JWT Verification failed: ${e.message}")
        }

        filterChain.doFilter(request, response)
    }

    private fun authenticateUser(parsedToken: String, request: HttpServletRequest) {
        val loginDetails = tokenService.extractLoginDetails(parsedToken)
        val user = userRepository.findById(loginDetails.id).orElse(null) ?: return

        if (loginDetails.provider == user.provider.toString()) {
            val authentication = UsernamePasswordAuthenticationToken(user, null, emptyList())
            authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authentication
        }
    }
}
