package org.depromeet.clog.server.domain.user.application

import jakarta.transaction.Transactional
import org.depromeet.clog.server.domain.auth.infrastructure.RefreshTokenRepository
import org.depromeet.clog.server.domain.common.ErrorCode
import org.depromeet.clog.server.domain.user.domain.UserRepository
import org.depromeet.clog.server.domain.user.presentation.exception.UserException
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository
) {

    @Transactional
    fun withdraw(userId: Long) {
        val user = userRepository.findByIdAndIsDeletedFalse(userId)
            ?: throw UserException(ErrorCode.USER_NOT_FOUND)

        user.isDeleted = true
        userRepository.save(user)

        refreshTokenRepository.deleteByUserIdAndProvider(userId, user.provider)
    }
}
