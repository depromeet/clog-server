package org.depromeet.cllog.server.domain.auth.application.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoTokenResponse(
    @JsonProperty("access_token") val accessToken: String
)
