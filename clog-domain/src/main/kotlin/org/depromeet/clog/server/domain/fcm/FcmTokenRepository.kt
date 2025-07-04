package org.depromeet.clog.server.domain.fcm

interface FcmTokenRepository {
    fun upsert(command: SaveFcmTokenCommand)
}
