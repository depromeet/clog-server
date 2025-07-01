package org.depromeet.clog.server.api.fcm.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.depromeet.clog.server.api.configuration.ApiConstants
import org.depromeet.clog.server.api.fcm.application.SaveFcmToken
import org.depromeet.clog.server.api.user.UserContext
import org.depromeet.clog.server.domain.common.ClogApiResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "FCM API", description = "FCM 푸시 토큰 저장 관련 API")
@RequestMapping("${ApiConstants.API_BASE_PATH_V1}/fcm")
@RestController
class FcmTokenCommandController(
    private val saveFcmToken: SaveFcmToken
) {

    @Operation(summary = "FCM 토큰 저장", description = "앱에서 발급받은 FCM 토큰을 저장합니다.")
    @PostMapping("/token")
    fun saveFcmToken(
        userContext: UserContext,
        @RequestBody request: SaveFcmTokenRequest,
    ): ClogApiResponse<Unit> {
        val command = request.toCommand(userContext.userId)
        saveFcmToken(command)
        return ClogApiResponse.from(Unit)
    }
}
