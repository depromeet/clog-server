package org.depromeet.cllog.server.domain.auth.application

import com.auth0.jwk.JwkProviderBuilder
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import org.depromeet.cllog.server.domain.auth.application.dto.AuthResponseDto
import org.depromeet.cllog.server.domain.auth.application.dto.KakaoAccount
import org.depromeet.cllog.server.domain.auth.application.dto.KakaoProfile
import org.depromeet.cllog.server.domain.auth.application.dto.KakaoUserInfo
import org.depromeet.cllog.server.domain.user.domain.Provider
import org.depromeet.cllog.server.domain.user.domain.User
import org.depromeet.cllog.server.domain.user.infrastructure.UserRepository
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import java.net.URL
import java.security.interfaces.RSAPublicKey

@Service
class AuthService(
    private val tokenService: TokenService,
    private val userRepository: UserRepository,
    private val restTemplate: RestTemplate
) {

    /**
     * OIDC 방식: 카카오 authorization code와 code verifier를 받아 토큰 교환 후,
     * 토큰 응답의 id_token을 검증하여 사용자 정보를 추출하고 JWT를 발급한다.
     */
    fun kakaoLoginWithCode(authorizationCode: String, codeVerifier: String): AuthResponseDto {
        // 카카오 토큰 엔드포인트에 PKCE 관련 파라미터를 포함하여 토큰 요청
        val tokenResponse = requestAccessToken(authorizationCode, codeVerifier)
        // OIDC 표준에 따라 id_token이 포함되어야 함
        val idToken = tokenResponse["id_token"] as? String
            ?: throw RuntimeException("카카오 토큰 응답에 id_token 누락")

        // OIDC: id_token 검증 및 사용자 정보 추출
        val kakaoUser = validateAndParseIdToken(idToken)

        // 기존 사용자 조회 또는 신규 사용자 등록
        val user = userRepository.findByLoginIdAndProvider(kakaoUser.id, Provider.KAKAO)
            .orElseGet { registerNewKakaoUser(kakaoUser) }

        // JWT 생성 후 반환
        return tokenService.generateTokens(user)
    }

    /**
     * 카카오 토큰 엔드포인트에 authorization code와 code verifier를 전달해 토큰 획득
     */
    private fun requestAccessToken(
        authorizationCode: String,
        codeVerifier: String
    ): Map<String, Any> {
        val headers = HttpHeaders().apply {
            contentType = MediaType.APPLICATION_FORM_URLENCODED
        }
        val body = LinkedMultiValueMap<String, String>().apply {
            add("grant_type", "authorization_code")
            add("client_id", "9231afba04c6e592c4d325fb72d8c89c")
            add("redirect_uri", "http://localhost:8080/login/oauth2/code/kakao")
            add("code", authorizationCode)
            add("code_verifier", codeVerifier)
        }
        val requestEntity = HttpEntity(body, headers)
        val responseEntity = restTemplate.exchange<Map<String, Any>>(
            "https://kauth.kakao.com/oauth/token",
            HttpMethod.POST,
            requestEntity,
            object : ParameterizedTypeReference<Map<String, Any>>() {}
        )
        return responseEntity.body ?: throw RuntimeException("카카오 토큰 응답 없음")
    }

    /**
     * id_token을 검증하고 OIDC 클레임에서 사용자 정보를 추출
     *
     *  issuer, audience, 만료시간, 서명 등 여러 요소를 검증해야 한다.
     * 여기서는 간단한 디코딩 예시를 보여준다.
     */
    private fun validateAndParseIdToken(idToken: String): KakaoUserInfo {
        try {
            // 1. JWKS 엔드포인트를 이용해 공개키 제공자 생성 (카카오의 경우)
            val jwksUrl = URL("https://kauth.kakao.com/.well-known/jwks.json")
            val jwkProvider = JwkProviderBuilder(jwksUrl).build()

            // 2. id_token 디코딩 (헤더에서 key id 추출)
            val decodedJWT = JWT.decode(idToken)
            val keyId = decodedJWT.keyId ?: throw RuntimeException("id_token에 key id가 없습니다.")

            // 3. 해당 key id에 해당하는 공개키를 가져오기
            val jwk = jwkProvider.get(keyId)
            val publicKey = jwk.publicKey as RSAPublicKey

            // 4. Algorithm 객체 생성
            val algorithm = Algorithm.RSA256(publicKey, null)

            // 5. JWTVerifier 생성: issuer, audience 등 검증 설정
            val verifier: JWTVerifier = JWT.require(algorithm)
                .withIssuer("https://kauth.kakao.com") // 카카오에서 발급한 id_token의 issuer
                .withAudience("9231afba04c6e592c4d325fb72d8c89c") // 클라이언트 아이디
                .build()

            // 6. 토큰 검증 (만료시간, 서명, issuer, audience 모두 체크)
            val verifiedJWT = verifier.verify(idToken)

            // 7. 클레임에서 사용자 정보 추출
            val subject =
                verifiedJWT.subject ?: throw RuntimeException("id_token에 sub(claim)이 없습니다.")
            val nickname = verifiedJWT.getClaim("nickname").asString() ?: "카카오 유저"

            return KakaoUserInfo(
                id = subject,
                kakaoAccount = KakaoAccount(
                    profile = KakaoProfile(nickname = nickname)
                )
            )
        } catch (e: Exception) {
            throw RuntimeException("id_token 검증 실패", e)
        }
    }

    /**
     * 신규 사용자 등록
     */
    private fun registerNewKakaoUser(kakaoUserInfo: KakaoUserInfo): User {
        val newUser = User(
            loginId = kakaoUserInfo.id,
            name = kakaoUserInfo.nickname,
            provider = Provider.KAKAO
        )
        return userRepository.save(newUser)
    }
}
