package org.depromeet.cllog.server.domain.auth.application.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class KakaoUserInfo(
    val id: String,  // ✅ 카카오에서 제공하는 고유 ID (loginId로 사용)
    @JsonProperty("kakao_account") val kakaoAccount: KakaoAccount
) {
    val nickname: String
        get() = kakaoAccount.profile.nickname // ✅ profile_nickname을 name으로 사용
}

data class KakaoAccount(
    val profile: KakaoProfile
)

data class KakaoProfile(
    @JsonProperty("nickname") val nickname: String
)
