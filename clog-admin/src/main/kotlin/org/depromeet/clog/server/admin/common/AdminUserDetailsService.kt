package org.depromeet.clog.server.admin.common

import org.depromeet.clog.server.domain.user.domain.Provider
import org.depromeet.clog.server.domain.user.domain.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AdminUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByLoginIdAndProvider(username, Provider.LOCAL)
            ?: throw UsernameNotFoundException("User not found with username: $username")

        return User.builder()
            .username(user.loginId)
            .password(user.name)
            .authorities(listOf(SimpleGrantedAuthority("ROLE_USER")))
            .build()
    }
}
