package org.depromeet.clog.server.domain.auth.application

import org.depromeet.clog.server.domain.auth.presentation.exception.AuthErrorCode
import org.depromeet.clog.server.domain.auth.presentation.exception.AuthException
import org.depromeet.clog.server.domain.user.domain.Provider
import org.depromeet.clog.server.domain.user.domain.User
import org.depromeet.clog.server.domain.user.infrastructure.UserRepository
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
        val kakaoId = oidcUser.attributes["sub"]?.toString()
            ?: throw AuthException(AuthErrorCode.AUTHENTICATION_FAILED)
        val nickname = (oidcUser.attributes["nickname"] as? String) ?: "카카오 유저"

        val user = userRepository.findByLoginIdAndProvider(kakaoId, Provider.KAKAO)
            ?: registerNewKakaoUser(kakaoId, nickname)

        tokenService.generateTokens(user)

        return oidcUser
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
