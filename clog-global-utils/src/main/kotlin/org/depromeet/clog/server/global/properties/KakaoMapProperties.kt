package org.depromeet.clog.server.global.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "kakao.map")
data class KakaoMapProperties(
    val restApiKey: String,
    val localSearchBaseUrl: String
)
