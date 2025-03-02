package org.depromeet.clog.server.infrastructure.user

import jakarta.persistence.*
import org.depromeet.clog.server.domain.user.domain.Provider
import org.depromeet.clog.server.domain.user.domain.User
import org.depromeet.clog.server.infrastructure.common.BaseEntity

@Table(name = "user")
@Entity
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val loginId: String,

    val name: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val provider: Provider,

    @Column(nullable = false)
    var isDeleted: Boolean = false
) : BaseEntity() {

    fun toDomain(): User = User(
        id = id,
        loginId = loginId,
        name = name,
        provider = provider,
        isDeleted = isDeleted
    )

    companion object{
        fun fromDomain(user: User): UserEntity = UserEntity(
            id = user.id,
            loginId = user.loginId,
            name = user.name,
            provider = user.provider,
            isDeleted = user.isDeleted
        )
    }
}
