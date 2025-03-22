package org.depromeet.clog.server.api.security

import org.depromeet.clog.server.api.configuration.ApiConstants.API_BASE_PATH_V1
import org.depromeet.clog.server.api.security.jwt.JwtFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(
    private val jwtFilter: JwtFilter,
    private val clogAuthenticationEntryPoint: ClogAuthenticationEntryPoint,
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .exceptionHandling {
                it.authenticationEntryPoint(clogAuthenticationEntryPoint)
            }
            .authorizeHttpRequests { auth ->
                auth.requestMatchers(
                    *PERMIT_ALL_PATTERNS.toTypedArray()
                ).permitAll()
                auth.anyRequest().authenticated()
            }
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

    companion object {
        private val PERMIT_ALL_PATTERNS = listOf(
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/actuator/**",
            "/v3/api-docs/**",
            "$API_BASE_PATH_V1/auth/**",
        )
    }
}
