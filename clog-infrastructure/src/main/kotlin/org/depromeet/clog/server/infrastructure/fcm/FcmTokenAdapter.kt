package org.depromeet.clog.server.infrastructure.fcm

import org.depromeet.clog.server.domain.fcm.FcmTokenRepository
import org.depromeet.clog.server.domain.fcm.SaveFcmTokenCommand
import org.depromeet.clog.server.infrastructure.user.UserJpaRepository
import org.springframework.stereotype.Component

@Component
class FcmTokenAdapter(
    private val fcmTokenJpaRepository: FcmTokenJpaRepository,
    private val userJpaRepository: UserJpaRepository
) : FcmTokenRepository {

    override fun upsert(command: SaveFcmTokenCommand) {
        val user = userJpaRepository.getReferenceById(command.userId)

        val existing = fcmTokenJpaRepository.findByUserId(command.userId)

        if (existing != null) {
            existing.token = command.token
            existing.device = command.device
        } else {
            val newEntity = FcmTokenEntity(
                user = user,
                token = command.token,
                device = command.device
            )
            fcmTokenJpaRepository.save(newEntity)
        }
    }
}
