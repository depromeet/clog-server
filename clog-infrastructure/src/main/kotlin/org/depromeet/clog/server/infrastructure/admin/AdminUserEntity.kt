package org.depromeet.clog.server.infrastructure.admin

import jakarta.persistence.*
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.depromeet.clog.server.infrastructure.common.BaseEntity
import org.hibernate.annotations.Comment

@Table(name = "admin_user")
@Entity
class AdminUserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Comment("어드민 로그인 아이디")
    @Column(nullable = false,)
    val loginId: String,

    @Comment("어드민 로그인 비밀번호")
    @Column(nullable = false)
    val password: String
) : BaseEntity()
