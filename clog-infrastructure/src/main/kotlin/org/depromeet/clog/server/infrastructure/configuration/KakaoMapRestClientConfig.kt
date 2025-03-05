package org.depromeet.clog.server.infrastructure.configuration

import io.github.oshai.kotlinlogging.KotlinLogging
import org.apache.hc.client5.http.impl.classic.HttpClients
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager
import org.depromeet.clog.server.infrastructure.configuration.properties.KakaoMapProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.http.MediaType
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestClient

@Configuration
@EnableConfigurationProperties(KakaoMapProperties::class)
class KakaoMapRestClientConfig(
    private val properties: KakaoMapProperties
) {

    private val logger = KotlinLogging.logger { KakaoMapRestClientConfig::class.java.name }

    companion object {
        const val MAX_CONNECTION_TOTAL = 3
        const val MAX_PER_ROUTE = 5
    }

    @Bean
    fun kakaoMapRestClient(): RestClient {
        return RestClient.builder()
            .requestFactory(httpRequestFactory())
            .baseUrl(properties.localSearchBaseUrl)
            .defaultHeaders { headers ->
                headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK ${properties.restApiKey}")
            }
            .defaultStatusHandler(HttpStatusCode::is5xxServerError) { request, response ->
                logger.error {
                    "Kakao Map API request failed. " +
                        "Status: ${response.statusCode}, " +
                        "URL: ${request.uri}, " +
                        "Method: ${request.method}"
                }
            }
            .build()
    }

    @Bean
    fun httpRequestFactory(): ClientHttpRequestFactory {
        val httpClient = HttpClients.custom()
            .setConnectionManager(pollConnectionManager())
            .build()

        return HttpComponentsClientHttpRequestFactory(httpClient)
    }

    @Bean
    fun pollConnectionManager(): PoolingHttpClientConnectionManager {
        val manager = PoolingHttpClientConnectionManager()
        manager.maxTotal = MAX_CONNECTION_TOTAL
        manager.defaultMaxPerRoute = MAX_PER_ROUTE

        return manager
    }
}
