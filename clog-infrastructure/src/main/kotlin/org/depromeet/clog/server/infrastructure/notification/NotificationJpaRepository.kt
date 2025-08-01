package org.depromeet.clog.server.infrastructure.notification

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface NotificationJpaRepository : JpaRepository<NotificationEntity, Long> {

    fun findByReceiverIdAndCreatedAtAfterOrderByCreatedAtDesc(
        receiverId: Long,
        createdAt: LocalDateTime,
        pageable: Pageable
    ): List<NotificationEntity>

    @Modifying
    @Query(
        """
        UPDATE NotificationEntity n
        SET n.isNewFlagCleared = true
        WHERE n.receiver.id = :userId AND n.isNewFlagCleared = false
        """
    )
    fun clearNewFlags(userId: Long)
}
