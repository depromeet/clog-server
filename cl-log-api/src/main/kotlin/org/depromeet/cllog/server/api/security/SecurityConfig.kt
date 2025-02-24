package org.depromeet.cllog.server.api.security

import jakarta.servlet.http.HttpServletResponse
import org.depromeet.cllog.server.api.security.jwt.JwtFilter
import org.depromeet.cllog.server.domain.auth.application.CustomOAuth2UserService
import org.depromeet.cllog.server.domain.auth.application.PrincipalDetailService
import org.depromeet.cllog.server.domain.auth.application.TokenService
import org.depromeet.cllog.server.domain.auth.presentation.exception.InvalidLoginException
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

/**
 *  OAuth2 로그인 성공 후 OAuth2LoginSuccessHandler 실행
 *  JwtFilter를 사용하여 JWT 인증 처리
 */
@Configuration
class SecurityConfig(
    private val userDetailsService: PrincipalDetailService,
    private val customOAuth2UserService: CustomOAuth2UserService,
    private val oAuth2LoginSuccessHandler: OAuth2LoginSuccessHandler,
    private val tokenService: TokenService
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it.requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(
                JwtFilter(tokenService, userDetailsService),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .exceptionHandling {
                it.authenticationEntryPoint { _, response, _ ->
                    response.status = HttpServletResponse.SC_UNAUTHORIZED
                    response.writer.write("Unauthorized")
                }
                it.accessDeniedHandler { _, response, _ ->
                    response.status = HttpServletResponse.SC_FORBIDDEN
                    response.writer.write("Forbidden")
                }
            }
            .oauth2Login {
                it.userInfoEndpoint { it.userService(customOAuth2UserService) }
                    .successHandler(oAuth2LoginSuccessHandler)
                    .failureHandler { _, _, _ -> throw InvalidLoginException() }
            }

        return http.build()
    }
}
