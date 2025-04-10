package org.depromeet.clog.server.api.auth.application

import org.depromeet.clog.server.domain.auth.infrastructure.RefreshTokenRepository
import org.depromeet.clog.server.domain.auth.presentation.exception.AuthException
import org.depromeet.clog.server.domain.common.ErrorCode
import org.depromeet.clog.server.domain.user.domain.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class LogoutService(
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository
) {
    fun logout(userId: Long) {
        val user = userRepository.findByIdAndIsDeletedFalse(userId)
            ?: throw AuthException(ErrorCode.USER_NOT_FOUND)

        refreshTokenRepository.deleteByUserIdAndProvider(userId, user.provider)
    }
}
