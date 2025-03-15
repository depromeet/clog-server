package org.depromeet.clog.server.domain.attempt.dto

import org.depromeet.clog.server.domain.attempt.AttemptStatus
import java.time.LocalDate

data class AttemptFolderView(
    val attemptId: Long,
    val videoId: Long,
    val videoLocalPath: String?,
    val videoThumbnailUrl: String?,
    val videoDurationMs: Long,
    val date: LocalDate,
    val cragName: String?,
    val colorName: String?,
    val colorHex: String?,
    val status: AttemptStatus
)
