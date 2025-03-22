package org.depromeet.clog.server.api.user.application

import org.depromeet.clog.server.domain.auth.infrastructure.RefreshTokenRepository
import org.depromeet.clog.server.domain.common.ErrorCode
import org.depromeet.clog.server.domain.user.application.dto.UpdateUserNameReauest
import org.depromeet.clog.server.domain.user.domain.Provider
import org.depromeet.clog.server.domain.user.domain.UserRepository
import org.depromeet.clog.server.domain.user.presentation.exception.MissingAppleAuthorizationCodeException
import org.depromeet.clog.server.domain.user.presentation.exception.UserException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val appleUserDeletionService: AppleUserDeletionService
) {
    fun withdraw(userId: Long, appleAuthorizationCode: String?) {
        val user = userRepository.findByIdAndIsDeletedFalse(userId)
            ?: throw UserException(ErrorCode.USER_NOT_FOUND)
        if (user.provider == Provider.APPLE) {
            if (appleAuthorizationCode.isNullOrBlank()) {
                throw MissingAppleAuthorizationCodeException()
            }
            appleUserDeletionService.revokeAppleAccount(appleAuthorizationCode)
        }

        user.isDeleted = true
        userRepository.save(user)

        refreshTokenRepository.deleteByUserIdAndProvider(userId, user.provider)
    }

    fun updateName(userId: Long, request: UpdateUserNameReauest) {
        val user = userRepository.findByIdAndIsDeletedFalse(userId)
            ?: throw UserException(ErrorCode.USER_NOT_FOUND)

        user.name = request.name
        userRepository.save(user)
    }
}
