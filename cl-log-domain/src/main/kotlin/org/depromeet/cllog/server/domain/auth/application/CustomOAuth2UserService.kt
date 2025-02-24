package org.depromeet.cllog.server.domain.auth.application

import org.depromeet.cllog.server.domain.auth.domain.CustomOAuth2User
import org.depromeet.cllog.server.domain.auth.presentation.exception.InvalidLoginException
import org.depromeet.cllog.server.domain.user.domain.Provider
import org.depromeet.cllog.server.domain.user.domain.User
import org.depromeet.cllog.server.domain.user.infrastructure.UserRepository
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
OAuth2 로그인 시 사용자 정보를 조회하고, 회원가입 또는 로그인 처리
isFirstLogin 값을 확인하여 신규 회원 여부 판단
로그인 성공 후 CustomOAuth2User 객체 반환
 */
@Service
@Transactional
class CustomOAuth2UserService(
    private val userRepository: UserRepository
) : DefaultOAuth2UserService() {

    override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
        val oAuth2User = super.loadUser(userRequest)
        val providerName = userRequest.clientRegistration.clientName.uppercase()
        val provider = getValidProvider(providerName)
        val attributes = oAuth2User.attributes
        val response = extractResponseAttributes(providerName, attributes)

        val loginId = resolveLoginId(providerName, attributes)
        val name = resolveName(providerName, response)
        val isFirstLogin = userRepository.findByLoginIdAndProvider(loginId, provider).isEmpty

        if (isFirstLogin) createUser(loginId, name, provider)

        return CustomOAuth2User.of(response, providerName, loginId, isFirstLogin)
    }

    private fun extractResponseAttributes(providerName: String, attributes: Map<String, Any>): Map<String, Any> {
        return when (providerName) {
            "KAKAO" -> attributes["kakao_account"] as? Map<String, Any> ?: throw InvalidLoginException()
            else -> attributes
        }
    }

    private fun resolveLoginId(providerName: String, attributes: Map<String, Any>): String {
        return when (providerName) {
            "KAKAO" -> attributes["id"].toString()
            "APPLE" -> attributes["sub"] as? String ?: throw InvalidLoginException()
            else -> throw InvalidLoginException()
        }
    }

    private fun resolveName(providerName: String, response: Map<String, Any>): String {
        return when (providerName) {
            "KAKAO" -> response["nickname"] as? String ?: ""
            "APPLE" -> "Apple User"
            else -> ""
        }
    }

    private fun createUser(loginId: String, name: String, provider: Provider) {
        val user = User(
            loginId = loginId,
            name = name,
            provider = provider
        )
        userRepository.save(user)
    }

    private fun getValidProvider(providerName: String): Provider {
        return when (providerName) {
            "KAKAO" -> Provider.KAKAO
            "APPLE" -> Provider.APPLE
            else -> throw InvalidLoginException()
        }
    }
}
