package org.depromeet.clog.server.api.user.presentation

import org.depromeet.clog.server.api.configuration.ApiConstants
import org.depromeet.clog.server.api.configuration.annotation.ApiErrorCodes
import org.depromeet.clog.server.domain.common.ClogApiResponse
import org.depromeet.clog.server.domain.common.ErrorCode
import org.depromeet.clog.server.domain.user.application.UserService
import org.depromeet.clog.server.domain.user.domain.UserContext
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("${ApiConstants.API_BASE_PATH_V1}/user")
class UserController(
    private val userService: UserService
) {

    @DeleteMapping("/leave")
    @ApiErrorCodes([ErrorCode.USER_NOT_FOUND])
    fun leave(userContext: UserContext): ClogApiResponse<Nothing?> {
        userService.withdraw(userContext.userId)
        return ClogApiResponse.from(null)
    }
}
