package org.depromeet.clog.server.domain.auth.application

import org.depromeet.clog.server.domain.auth.infrastructure.RefreshTokenRepository
import org.depromeet.clog.server.domain.auth.presentation.exception.AuthException
import org.depromeet.clog.server.domain.common.ErrorCode
import org.depromeet.clog.server.domain.user.infrastructure.UserRepository
import org.springframework.stereotype.Service

@Service
class LogoutService(
    private val tokenService: TokenService,
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    fun logout(token: String) {
        val loginDetails = tokenService.extractLoginDetails(token)

        val user = userRepository.findByLoginIdAndProvider(loginDetails.loginId, loginDetails.provider)
            ?: throw AuthException(ErrorCode.USER_NOT_FOUND)

        refreshTokenRepository.deleteByUserIdAndProvider(user.id!!, user.provider)
    }
}
