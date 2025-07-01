package org.depromeet.clog.server.api.fcm.presentation

import io.swagger.v3.oas.annotations.media.Schema
import org.depromeet.clog.server.domain.fcm.SaveFcmTokenCommand

@Schema(description = "FCM 토큰 저장 요청")
data class SaveFcmTokenRequest(

    @Schema(description = "FCM 토큰", example = "fcm_token_example_123")
    val token: String,

    @Schema(description = "디바이스 정보", example = "iPhone 15 Pro")
    val device: String? = null,
) {
    fun toCommand(userId: Long): SaveFcmTokenCommand {
        return SaveFcmTokenCommand(
            userId = userId,
            token = this.token,
            device = this.device
        )
    }
}
