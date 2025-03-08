package org.depromeet.clog.server.api.configuration

import jakarta.servlet.http.HttpServletRequest
import org.depromeet.clog.server.api.security.jwt.JwtUtils
import org.depromeet.clog.server.domain.auth.application.TokenService
import org.depromeet.clog.server.domain.user.domain.UserContext
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class UserContextResolver(
    private val tokenService: TokenService,
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.parameterType == UserContext::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): UserContext {
        val request = webRequest.nativeRequest as HttpServletRequest
        val token = JwtUtils.extractToken(request)
            ?: throw IllegalArgumentException("Token is missing")
        val loginDetails = tokenService.extractLoginDetails(token)

        return UserContext(
            userId = loginDetails.loginId.toLong(),
        )
    }
}
