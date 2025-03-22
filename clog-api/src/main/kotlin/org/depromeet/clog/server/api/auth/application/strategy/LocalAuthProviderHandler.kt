package org.depromeet.clog.server.api.auth.application.strategy

import org.depromeet.clog.server.api.auth.application.TokenService
import org.depromeet.clog.server.api.auth.application.dto.request.LocalLoginRequest
import org.depromeet.clog.server.api.auth.application.dto.response.AuthResponseDto
import org.depromeet.clog.server.domain.user.domain.Provider
import org.depromeet.clog.server.domain.user.domain.User
import org.depromeet.clog.server.domain.user.domain.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class LocalAuthProviderHandler(
    private val tokenService: TokenService,
    private val userRepository: UserRepository
) : AuthProviderHandler<LocalLoginRequest> {

    override fun login(request: LocalLoginRequest): AuthResponseDto {
        val user = userRepository.findByLoginIdAndProviderAndIsDeletedFalse(
            request.loginId,
            Provider.LOCAL
        )
            ?: registerNewLocalUser(request.loginId)
        return tokenService.generateTokens(user)
    }

    private fun registerNewLocalUser(loginId: String): User {
        val existingUser = userRepository.findByLoginIdAndProvider(loginId, Provider.LOCAL)

        return if (existingUser != null && existingUser.isDeleted) {
            existingUser.isDeleted = false
            userRepository.save(existingUser)
        } else {
            val newUser = User(
                loginId = loginId,
                name = "로컬_$loginId",
                provider = Provider.LOCAL
            )
            userRepository.save(newUser)
        }
    }
}
