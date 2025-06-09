package org.depromeet.clog.server.api.user.application

import org.depromeet.clog.server.domain.user.domain.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AddFollowing(
    private val userRepository: UserRepository,
) {

    @Transactional
    operator fun invoke(
        requestedUserId: Long,
        targetUserId: Long
    ) {
        userRepository.addFollowing(
            requestedUserId = requestedUserId,
            targetUserId = targetUserId
        )
    }
}
