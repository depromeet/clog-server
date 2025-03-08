package org.depromeet.clog.server.domain.auth.application.strategy

import jakarta.transaction.Transactional
import org.depromeet.clog.server.domain.auth.application.TokenService
import org.depromeet.clog.server.domain.auth.application.dto.AuthResponseDto
import org.depromeet.clog.server.domain.auth.application.dto.LocalLoginRequest
import org.depromeet.clog.server.domain.user.domain.Provider
import org.depromeet.clog.server.domain.user.domain.User
import org.depromeet.clog.server.domain.user.domain.UserRepository
import org.springframework.stereotype.Service

@Service
@Transactional
class LocalAuthProviderHandler(
    private val tokenService: TokenService,
    private val userRepository: UserRepository
) : AuthProviderHandler<LocalLoginRequest> {

    override fun login(request: LocalLoginRequest): AuthResponseDto {
        val user = userRepository.findByLoginIdAndProvider(request.loginId, Provider.LOCAL)
            ?: registerNewLocalUser(request.loginId)
        return tokenService.generateTokens(user)
    }

    private fun registerNewLocalUser(loginId: String): User {
        val newUser = User(
            loginId = loginId,
            name = "로컬_$loginId",
            provider = Provider.LOCAL
        )
        return userRepository.save(newUser)
    }
}
