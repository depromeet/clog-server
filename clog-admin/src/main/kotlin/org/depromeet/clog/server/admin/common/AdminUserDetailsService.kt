package org.depromeet.clog.server.admin.common

import org.depromeet.clog.server.domain.admin.AdminUserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AdminUserDetailsService(
    private val adminUserRepository: AdminUserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = adminUserRepository.findByLoginId(username)

        return User.builder()
            .username(user.loginId)
            .password(user.password)
            .authorities(listOf(SimpleGrantedAuthority("ROLE_ADMIN")))
            .build()
    }
}
