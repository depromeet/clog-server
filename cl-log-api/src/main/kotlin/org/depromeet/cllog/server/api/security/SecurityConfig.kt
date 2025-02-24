package org.depromeet.cllog.server.api.security

import org.depromeet.cllog.server.api.security.jwt.JwtFilter
import org.depromeet.cllog.server.domain.auth.application.TokenService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(
    private val tokenService: TokenService,
    private val principalDetailsService: PrincipalDetailService, // 추가
    private val oAuth2LoginSuccessHandler: OAuth2LoginSuccessHandler // Bean 주입
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it.requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/api/auth/oauth2/**").permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(JwtFilter(tokenService, principalDetailsService), UsernamePasswordAuthenticationFilter::class.java) // principalDetailsService 추가
            .oauth2Login {
                it.successHandler(oAuth2LoginSuccessHandler) // Bean을 주입받아 사용
            }

        return http.build()
    }
}
