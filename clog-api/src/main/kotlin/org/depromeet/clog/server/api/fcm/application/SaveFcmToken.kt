package org.depromeet.clog.server.api.fcm.application

import org.depromeet.clog.server.domain.fcm.FcmTokenRepository
import org.depromeet.clog.server.domain.fcm.SaveFcmTokenCommand
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SaveFcmToken(
    private val fcmTokenRepository: FcmTokenRepository
) {
    operator fun invoke(command: SaveFcmTokenCommand) {
        fcmTokenRepository.upsert(command)
    }
}
