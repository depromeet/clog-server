package org.depromeet.cllog.server.api.security

import org.depromeet.cllog.server.api.security.OAuth2LoginSuccessHandler
import org.depromeet.cllog.server.api.security.jwt.JwtFilter
import org.depromeet.cllog.server.domain.auth.application.CustomOAuth2UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(
    private val jwtFilter: JwtFilter
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { auth ->
                auth.requestMatchers("/auth/**").permitAll() // ✅ 인증 없이 접근 가능하도록 설정
                auth.anyRequest().authenticated() // ✅ 그 외 요청은 인증 필요
            }
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)

        return http.build() // ✅ `SecurityFilterChain` Bean으로 반환
    }
}
