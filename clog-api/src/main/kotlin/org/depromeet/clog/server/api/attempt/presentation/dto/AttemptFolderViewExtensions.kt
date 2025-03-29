package org.depromeet.clog.server.api.attempt.presentation.dto

import org.depromeet.clog.server.api.crag.presentation.dto.CragResponse
import org.depromeet.clog.server.api.grade.presentation.dto.ColorResponse
import org.depromeet.clog.server.domain.attempt.dto.AttemptFolderView

fun AttemptFolderView.toGetAttemptDetailResponse(): GetAttemptDetailResponse {
    return GetAttemptDetailResponse(
        attemptId = this.attemptId,
        video = AttemptVideoResponse(
            id = this.videoId,
            localPath = this.videoLocalPath ?: "",
            thumbnailUrl = this.videoThumbnailUrl,
            durationMs = this.videoDurationMs
        ),
        date = this.date,
        crag = this.cragId?.let {
            CragResponse(
                id = it,
                name = this.cragName!!,
                roadAddress = this.cragRoadAddress!!
            )
        },
        color = this.colorId?.let {
            ColorResponse(
                id = it,
                name = this.colorName!!,
                hex = this.colorHex!!
            )
        },
        status = this.status
    )
}
