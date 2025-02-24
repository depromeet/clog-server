package org.depromeet.cllog.server.domain.auth.application.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoUserInfo(
    val id: String,
    @JsonProperty("kakao_account") val kakaoAccount: KakaoAccount
) {
    val nickname: String
        get() = kakaoAccount.profile.nickname
}

data class KakaoAccount(
    val profile: KakaoProfile
)

data class KakaoProfile(
    @JsonProperty("nickname") val nickname: String
)
