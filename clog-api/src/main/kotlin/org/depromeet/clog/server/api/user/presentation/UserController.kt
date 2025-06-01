package org.depromeet.clog.server.api.user.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.depromeet.clog.server.api.auth.application.LogoutService
import org.depromeet.clog.server.api.configuration.ApiConstants.API_BASE_PATH_V1
import org.depromeet.clog.server.api.configuration.annotation.ApiErrorCodes
import org.depromeet.clog.server.api.user.UserContext
import org.depromeet.clog.server.api.user.application.UpdateUser
import org.depromeet.clog.server.api.user.application.UserService
import org.depromeet.clog.server.api.user.presentation.dto.WithdrawalRequest
import org.depromeet.clog.server.domain.common.ClogApiResponse
import org.depromeet.clog.server.domain.common.ErrorCode
import org.depromeet.clog.server.domain.user.application.dto.UpdateUserNameReauest
import org.springframework.web.bind.annotation.*

@Tag(name = "유저 API", description = "유저 관련 작업을 수행합니다.")
@RestController
@RequestMapping("$API_BASE_PATH_V1/users")
class UserController(
    private val userService: UserService,
    private val logoutService: LogoutService,
    private val updateUser: UpdateUser,
) {

    @Operation(summary = "로그아웃")
    @PostMapping("/log-out")
    @ApiErrorCodes([ErrorCode.USER_NOT_FOUND])
    fun logout(userContext: UserContext): ClogApiResponse<Nothing?> {
        logoutService.logout(userContext.userId)
        return ClogApiResponse.from(null)
    }

    @Operation(summary = "회원탈퇴", description = "애플 회원일 경우 authorizationCode를 같이 보내주세요.")
    @DeleteMapping("/leave")
    @ApiErrorCodes([ErrorCode.USER_NOT_FOUND])
    fun leave(
        userContext: UserContext,
        @RequestBody(required = false) request: WithdrawalRequest?
    ): ClogApiResponse<Nothing?> {
        userService.withdraw(userContext.userId, request?.authorizationCode)
        return ClogApiResponse.from(null)
    }

    @Operation(summary = "이름 변경")
    @PatchMapping("/name")
    @ApiErrorCodes([ErrorCode.USER_NOT_FOUND])
    fun updateName(
        userContext: UserContext,
        @RequestBody request: UpdateUserNameReauest
    ): ClogApiResponse<Nothing?> {
        userService.updateName(userContext.userId, request)
        return ClogApiResponse.from(null)
    }

    @Operation(summary = "유저 정보 변경")
    @PatchMapping
    @ApiErrorCodes([ErrorCode.USER_NOT_FOUND])
    fun update(
        userContext: UserContext,
        @RequestBody @Valid request: UpdateUser.Command
    ): ClogApiResponse<UpdateUser.Result> {
        val result = updateUser(userContext.userId, request)
        return ClogApiResponse.from(result)
    }
}
