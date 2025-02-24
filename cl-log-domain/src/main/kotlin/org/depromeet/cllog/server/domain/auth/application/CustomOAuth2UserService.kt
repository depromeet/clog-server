package org.depromeet.cllog.server.domain.auth.application

import org.depromeet.cllog.server.domain.auth.application.dto.AuthResponseDto
import org.depromeet.cllog.server.domain.user.domain.Provider
import org.depromeet.cllog.server.domain.user.domain.User
import org.depromeet.cllog.server.domain.user.infrastructure.UserRepository
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.stereotype.Service

@Service
class CustomOAuth2UserService(
    private val userRepository: UserRepository,
    private val tokenService: TokenService
) : OidcUserService() {

    override fun loadUser(userRequest: OidcUserRequest): OidcUser {
        val oidcUser = super.loadUser(userRequest)
        val kakaoId = oidcUser.attributes["sub"]?.toString() ?: throw RuntimeException("카카오 ID 누락")
        val nickname = (oidcUser.attributes["nickname"] as? String) ?: "카카오 유저"

        val user = userRepository.findByLoginIdAndProvider(kakaoId, Provider.KAKAO)
            .orElseGet { registerNewKakaoUser(kakaoId, nickname) }

        // ✅ JWT를 SecurityContext에 저장
        val authResponse = tokenService.generateTokens(user)

        return oidcUser // ✅ OidcUser 반환 (Spring Security 로그인 흐름 유지)
    }

    private fun registerNewKakaoUser(loginId: String, name: String): User {
        val newUser = User(
            loginId = loginId,
            name = name,
            provider = Provider.KAKAO
        )
        return userRepository.save(newUser)
    }
}
