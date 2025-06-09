package org.depromeet.clog.server.api.user.application

import org.depromeet.clog.server.domain.user.domain.UserRepository
import org.depromeet.clog.server.domain.user.domain.exception.UserNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CancelFollowing(
    private val userRepository: UserRepository,
) {

    @Transactional
    operator fun invoke(
        requestedUserId: Long,
        targetUserId: Long
    ) {
        val requestedUser = userRepository.findByIdAndIsDeletedFalse(requestedUserId)
            ?: throw UserNotFoundException()

        userRepository.deleteFollowing(
            requestedUserId = requestedUser.id!!,
            targetUserId = targetUserId
        )
    }
}
