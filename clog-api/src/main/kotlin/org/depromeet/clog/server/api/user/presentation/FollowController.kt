package org.depromeet.clog.server.api.user.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.depromeet.clog.server.api.configuration.ApiConstants
import org.depromeet.clog.server.api.user.UserContext
import org.depromeet.clog.server.api.user.application.CancelFollow
import org.depromeet.clog.server.api.user.application.FollowService
import org.depromeet.clog.server.domain.common.ClogApiResponse
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "팔로우/팔로잉 API", description = "팔로우 및 팔로잉 관련 작업을 수행합니다.")
@RestController
@RequestMapping("${ApiConstants.API_BASE_PATH_V1}/users")
class FollowController(
    private val followService: FollowService,
    private val cancelFollow: CancelFollow,
) {

    @Operation(summary = "팔로워 목록 조회")
    @GetMapping("/me/followers")
    fun getFollowers(userContext: UserContext): ClogApiResponse<List<UserResponse>> {
        val results = followService.getFollowers(userContext.userId)

        return ClogApiResponse.from(results)
    }

    @Operation(summary = "팔로잉 목록 조회")
    @GetMapping("/me/followings")
    fun getFollowing(userContext: UserContext): ClogApiResponse<List<UserResponse>> {
        val results = followService.getFollowing(userContext.userId)

        return ClogApiResponse.from(results)
    }

    @Operation(summary = "팔로잉 취소")
    @DeleteMapping("/me/followings/{targetUserId}")
    fun unfollow(
        userContext: UserContext,
        @PathVariable targetUserId: Long
    ): ClogApiResponse<Boolean> {
        cancelFollow(userContext.userId, targetUserId)
        return ClogApiResponse.from(true)
    }
}
