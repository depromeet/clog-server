package org.depromeet.clog.server.api.security.jwt

import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.depromeet.clog.server.domain.auth.application.TokenService
import org.depromeet.clog.server.domain.auth.presentation.exception.AuthException
import org.depromeet.clog.server.domain.user.infrastructure.UserRepository
import org.slf4j.LoggerFactory
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

    private val logger = LoggerFactory.getLogger(JwtFilter::class.java)

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
                logger.warn("사용자 없음: ${loginDetails.loginId}")
                filterChain.doFilter(request, response)
                return
            }
            if (loginDetails.provider == user.provider) {
                val authentication = UsernamePasswordAuthenticationToken(user, null, emptyList())
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (e: AuthException) {
            logger.error("Auth exception 발생: ${e.message}", e)
        } catch (e: TokenExpiredException) {
            logger.error("JWT Token expired: ${e.message}", e)
        } catch (e: JWTVerificationException) {
            logger.error("JWT Verification failed: ${e.message}", e)
        }

        filterChain.doFilter(request, response)
    }

    private fun extractToken(request: HttpServletRequest): String? {
        return request.getHeader(HttpHeaders.AUTHORIZATION)
            ?.removePrefix("Bearer ")
            ?.takeIf { it.isNotBlank() }
    }
}
