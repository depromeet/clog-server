package org.depromeet.clog.server.api.attempt.presentation.dto

import org.depromeet.clog.server.domain.attempt.dto.AttemptFolderView

fun AttemptFolderView.toGetAttemptDetailResponse(): GetAttemptDetailResponse {
    return GetAttemptDetailResponse(
        attemptId = this.attemptId,
        videoId = this.videoId,
        videoLocalPath = this.videoLocalPath ?: "",
        videoThumbnailUrl = this.videoThumbnailUrl ?: "",
        videoDurationMs = this.videoDurationMs,
        date = this.date,
        cragName = this.cragName ?: "",
        colorName = this.colorName ?: "",
        colorHex = this.colorHex ?: "",
        status = this.status
    )
}
