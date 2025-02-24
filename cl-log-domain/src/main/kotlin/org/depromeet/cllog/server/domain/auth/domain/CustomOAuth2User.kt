package org.depromeet.cllog.server.domain.auth.domain

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User

class CustomOAuth2User(
    private val attributes: Map<String, Any>,
    provider: String,
    private val principalName: String,
    val isFirstLogin: Boolean
) : OAuth2User {

    val provider: String = provider.uppercase()

    companion object {
        fun of(
            attributes: Map<String, Any>,
            providerName: String,
            email: String,
            isFirstLogin: Boolean
        ): CustomOAuth2User {
            return CustomOAuth2User(attributes, providerName, email, isFirstLogin)
        }
    }

    override fun getAttributes(): Map<String, Any> = attributes

    override fun getAuthorities(): Collection<GrantedAuthority> = emptyList()

    override fun getName(): String = principalName

    fun getEmail(): String? = attributes["email"] as? String
}
