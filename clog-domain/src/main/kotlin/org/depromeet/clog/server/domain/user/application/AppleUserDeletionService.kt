package org.depromeet.clog.server.domain.user.application

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.depromeet.clog.server.domain.user.presentation.exception.AppleRevokeException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import java.security.KeyFactory
import java.security.spec.PKCS8EncodedKeySpec
import java.util.*

@Service
class AppleUserDeletionService(
    @Value("\${apple.client-id}") private val appleClientId: String,
    @Value("\${apple.team-id}") private val appleTeamId: String,
    @Value("\${apple.key-id}") private val appleKeyId: String,
    @Value("\${apple.private-key}") private val applePrivateKey: String,
    private val restTemplate: RestTemplate
) {

    /**
     * 클라이언트로부터 전달받은 authorizationCode(실제로는 리프레시 토큰 역할을 할 값)를 이용하여
     * 애플의 revoke endpoint (https://appleid.apple.com/auth/revoke)에 요청을 보내 탈퇴 처리를 진행함.
     */
    fun revokeAppleAccount(authorizationCode: String) {
        val url = "https://appleid.apple.com/auth/revoke"
        val clientSecret = generateAppleClientSecret()

        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
        }
        val body = LinkedMultiValueMap<String, String>().apply {
            add("client_id", appleClientId)
            add("client_secret", clientSecret)
            add("token", authorizationCode)
            add("token_type_hint", "refresh_token")
        }
        val requestEntity = HttpEntity(body, headers)

        try {
            restTemplate.postForEntity(url, requestEntity, String::class.java)
        } catch (e: Exception) {
            throw AppleRevokeException("애플 계정 탈퇴 처리 중 revoke 요청 실패", e)
        }
    }

    /**
     * 애플 클라이언트 시크릿 생성
     */
    private fun generateAppleClientSecret(): String {
        val nowMillis = System.currentTimeMillis()
        val expMillis = nowMillis + 180L * 24 * 60 * 60 * 1000

        val formattedKey = "-----BEGIN PRIVATE KEY-----\n" + applePrivateKey.replace("\\\\n", "\n")
            .trim() + "\n-----END PRIVATE KEY-----"
        val decodedKey = Base64.getDecoder().decode(
            formattedKey
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replace("\\s".toRegex(), "")
        )
        val keyFactory = KeyFactory.getInstance("EC")
        val keySpec = PKCS8EncodedKeySpec(decodedKey)
        val privateKey = keyFactory.generatePrivate(keySpec)
        return Jwts.builder()
            .setHeaderParam("kid", appleKeyId)
            .setIssuer(appleTeamId)
            .setIssuedAt(Date(nowMillis))
            .setExpiration(Date(expMillis))
            .setAudience("https://appleid.apple.com")
            .setSubject(appleClientId)
            .signWith(privateKey, SignatureAlgorithm.ES256)
            .compact()
    }
}
