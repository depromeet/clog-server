package org.depromeet.clog.server.infrastructure.user

import jakarta.persistence.*
import org.depromeet.clog.server.domain.user.domain.Provider
import org.depromeet.clog.server.infrastructure.common.BaseEntity

@Table(name = "user")
@Entity
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val loginId: String,

    val name: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val provider: Provider,

    @Column(nullable = false)
    var isDeleted: Boolean = false,

    @Column
    val height: Int? = null,

    @Column
    val armSpan: Int? = null,

    @Column
    val instagramUrl: String? = null,
) : BaseEntity()
