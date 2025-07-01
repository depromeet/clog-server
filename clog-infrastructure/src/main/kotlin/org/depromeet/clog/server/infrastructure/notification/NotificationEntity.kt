package org.depromeet.clog.server.infrastructure.notification

import jakarta.persistence.*
import org.depromeet.clog.server.domain.notification.NotificationType
import org.depromeet.clog.server.infrastructure.common.BaseEntity
import org.depromeet.clog.server.infrastructure.user.UserEntity
import java.time.LocalDateTime

@Table(name = "notification")
@Entity
class NotificationEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    val receiver: UserEntity,

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    val type: NotificationType,

    @Column(name = "target_id")
    val targetId: Long,

    @Column(name = "title")
    val title: String,

    @Column(name = "message")
    val message: String,

    @Column(name = "is_read")
    val isRead: Boolean = false,

    @Column(name = "read_at")
    val readAt: LocalDateTime? = null,

    @Column(name = "is_new_flag_cleared")
    val isNewFlagCleared: Boolean = false,
) : BaseEntity()
