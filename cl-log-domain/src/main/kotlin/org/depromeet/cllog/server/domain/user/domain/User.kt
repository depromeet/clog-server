package org.depromeet.cllog.server.domain.user.domain

import jakarta.persistence.*

@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    val loginId: String,

    val name: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val provider: Provider,

    @Column(nullable = false)
    var isDeleted: Boolean = false
) {
    fun isActive(): Boolean {
        return !isDeleted
    }
}
