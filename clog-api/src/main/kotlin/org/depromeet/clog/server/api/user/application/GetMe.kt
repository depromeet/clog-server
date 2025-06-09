package org.depromeet.clog.server.api.user.application

import org.depromeet.clog.server.api.user.UserContext
import org.depromeet.clog.server.api.user.presentation.UserResponse
import org.depromeet.clog.server.domain.user.domain.UserRepository
import org.depromeet.clog.server.domain.user.domain.exception.UserNotFoundException
import org.springframework.stereotype.Service

@Service
class GetMe(
    private val userRepository: UserRepository,
) {

    operator fun invoke(userContext: UserContext): UserResponse {
        val user = userRepository.findByIdAndIsDeletedFalse(userContext.userId)
            ?: throw UserNotFoundException()

        return UserResponse(
            id = user.id!!,
            name = user.name,
            height = user.height,
            armSpan = user.armSpan,
            instagramUrl = user.instagramUrl,
            isFollowing = true,
        )
    }
}
