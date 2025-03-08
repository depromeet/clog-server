package org.depromeet.clog.server.api.user.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.depromeet.clog.server.api.configuration.ApiConstants
import org.depromeet.clog.server.api.configuration.annotation.ApiErrorCodes
import org.depromeet.clog.server.api.user.application.GetMe
import org.depromeet.clog.server.domain.common.ClogApiResponse
import org.depromeet.clog.server.domain.common.ErrorCode
import org.depromeet.clog.server.domain.user.domain.UserContext
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "유저 조회", description = "유저 조회 API")
@RestController
@RequestMapping("${ApiConstants.API_BASE_PATH_V1}/users")
class UserQueryController(
    private val getMe: GetMe,
) {

    @Operation(summary = "내 정보 조회")
    @ApiErrorCodes([ErrorCode.USER_NOT_FOUND])
    @GetMapping("/me")
    fun me(userContext: UserContext): ClogApiResponse<UserResponse> {
        val response = getMe(userContext)

        return ClogApiResponse.from(response)
    }
}
