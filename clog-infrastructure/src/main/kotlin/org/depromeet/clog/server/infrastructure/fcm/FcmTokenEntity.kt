package org.depromeet.clog.server.infrastructure.fcm

import jakarta.persistence.*
import org.depromeet.clog.server.infrastructure.common.BaseEntity
import org.depromeet.clog.server.infrastructure.user.UserEntity

@Table(name = "fcm_token")
@Entity
class FcmTokenEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    val user: UserEntity,

    @Column(name = "token")
    var token: String,

    @Column(name = "device")
    var device: String? = null,
) : BaseEntity()
