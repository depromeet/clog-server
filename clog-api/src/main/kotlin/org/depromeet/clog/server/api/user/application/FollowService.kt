package org.depromeet.clog.server.api.user.application

import org.depromeet.clog.server.api.user.presentation.UserResponse
import org.depromeet.clog.server.domain.user.domain.FollowRepository
import org.springframework.stereotype.Service

@Service
class FollowService(
    private val followRepository: FollowRepository,
) {
    fun getFollowers(userId: Long): List<UserResponse> {
        return followRepository.findFollowersByUserId(userId)
            .map { UserResponse.from(it) }
    }

    fun getFollowing(userId: Long): List<UserResponse> {
        return followRepository.findFollowingByUserId(userId)
            .map { UserResponse.from(it) }
    }
}
