package org.depromeet.cllog.server.domain.user.infrastructure

import org.depromeet.cllog.server.domain.user.domain.Provider
import org.depromeet.cllog.server.domain.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByLoginIdAndProvider(loginId: String, provider: Provider): Optional<User>

    @Query("SELECT u FROM User u WHERE u.loginId = :loginId AND u.provider = :provider AND u.isDeleted = false")
    fun findActiveUserByLoginIdAndProvider(
        @Param("loginId") loginId: String,
        @Param("provider") provider: Provider
    ): Optional<User>

}
