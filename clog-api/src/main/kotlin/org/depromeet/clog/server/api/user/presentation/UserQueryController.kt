package org.depromeet.clog.server.api.user.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.depromeet.clog.server.api.configuration.ApiConstants
import org.depromeet.clog.server.api.configuration.annotation.ApiErrorCodes
import org.depromeet.clog.server.api.user.UserContext
import org.depromeet.clog.server.api.user.application.GetMe
import org.depromeet.clog.server.api.user.application.SearchUsers
import org.depromeet.clog.server.domain.common.ClogApiResponse
import org.depromeet.clog.server.domain.common.ErrorCode
import org.depromeet.clog.server.domain.user.domain.UserQuery
import org.depromeet.clog.server.global.utils.dto.CursorPagination
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "유저 API", description = "유저 관련 작업을 수행합니다.")
@RestController
@RequestMapping("${ApiConstants.API_BASE_PATH_V1}/users")
class UserQueryController(
    private val getMe: GetMe,
    private val searchUsers: SearchUsers,
) {

    @Operation(summary = "내 정보 조회")
    @ApiErrorCodes([ErrorCode.USER_NOT_FOUND])
    @GetMapping("/me")
    fun me(userContext: UserContext): ClogApiResponse<UserResponse> {
        val response = getMe(userContext)

        return ClogApiResponse.from(response)
    }

    @Operation(summary = "유저 검색")
    @GetMapping("/search")
    fun search(
        userContext: UserContext,
        @ModelAttribute query: UserQuery,
    ): ClogApiResponse<CursorPagination.Response<Long, UserResponse>> {
        val result = searchUsers(
            userId = userContext.userId,
            query = query,
        )

        return ClogApiResponse.from(result)
    }
}
