package org.depromeet.cllog.server.api.security.jwt

import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.depromeet.cllog.server.domain.auth.application.PrincipalDetailService
import org.depromeet.cllog.server.domain.auth.application.TokenService
import org.depromeet.cllog.server.domain.auth.domain.PrincipalDetails
import org.depromeet.cllog.server.domain.auth.presentation.exception.TokenNotFoundException
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
    private val principalDetailsService: PrincipalDetailService
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
            saveUserDetails(parsedToken, request)
        } catch (e: TokenNotFoundException) {
            logger.error("JWT Token not found: ${e.message}")
        } catch (e: TokenExpiredException) {
            logger.error("JWT Token expired: ${e.message}")
        } catch (e: JWTVerificationException) {
            logger.error("JWT Verification failed: ${e.message}")
        }

        filterChain.doFilter(request, response)
    }

    private fun saveUserDetails(parsedToken: String, request: HttpServletRequest) {
        val loginDetails = tokenService.extractLoginDetails(parsedToken)
        val userDetails = principalDetailsService.loadUserByUsername(loginDetails) as PrincipalDetails
        val user = userDetails.getUser()

        if (loginDetails.id == user.id && loginDetails.provider == user.provider.toString()) {
            val authentication = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
            authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authentication
        }
    }
}
