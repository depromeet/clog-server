package org.depromeet.clog.server.api.user.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "애플 회원일 경우 탈퇴에 필요한 authorizationCode 요청 DTO")
data class WithdrawalRequest(
    @Schema(description = "애플에서 제공한 authorizationCode")
    val authorizationCode: String?
)
