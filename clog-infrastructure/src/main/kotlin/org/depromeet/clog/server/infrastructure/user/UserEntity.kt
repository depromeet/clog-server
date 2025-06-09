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
    val height: Double? = null,

    @Column
    val armSpan: Double? = null,

    @Column
    val instagramUrl: String? = null,

    @ManyToMany
    @JoinTable(
        name = "follow_relation",
        joinColumns = [JoinColumn(name = "follower_id")],
        inverseJoinColumns = [JoinColumn(name = "following_id")],
    )
    val followings: MutableSet<UserEntity> = mutableSetOf(),

    @ManyToMany(mappedBy = "followings")
    val followers: MutableSet<UserEntity> = mutableSetOf()
) : BaseEntity()
