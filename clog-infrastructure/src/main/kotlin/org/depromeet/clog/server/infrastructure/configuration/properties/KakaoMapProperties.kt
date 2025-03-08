package org.depromeet.clog.server.infrastructure.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "kakao.map")
data class KakaoMapProperties(
    val restApiKey: String,
    val localSearchBaseUrl: String
)
