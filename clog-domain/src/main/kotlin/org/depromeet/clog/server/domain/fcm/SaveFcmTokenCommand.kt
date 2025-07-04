package org.depromeet.clog.server.domain.fcm

data class SaveFcmTokenCommand(
    val userId: Long,
    val token: String,
    val device: String?,
)
