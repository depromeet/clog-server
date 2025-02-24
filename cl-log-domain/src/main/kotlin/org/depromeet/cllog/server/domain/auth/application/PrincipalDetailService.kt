package org.depromeet.cllog.server.domain.auth.application

import org.depromeet.cllog.server.domain.auth.application.dto.LoginDetails
import org.depromeet.cllog.server.domain.auth.domain.PrincipalDetails
import org.depromeet.cllog.server.domain.auth.presentation.exception.UserNotFoundException
import org.depromeet.cllog.server.domain.user.domain.Provider
import org.depromeet.cllog.server.domain.user.domain.User
import org.depromeet.cllog.server.domain.user.infrastructure.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PrincipalDetailService(
    private val userRepository: UserRepository
) {
    fun loadUserByUsername(loginDetails: LoginDetails): UserDetails {
        val user: User = userRepository
            .findActiveUserByLoginIdAndProvider(loginDetails.loginId, Provider.valueOf(loginDetails.provider))
            .orElseThrow { UserNotFoundException() }
        return PrincipalDetails(user)
    }
}
