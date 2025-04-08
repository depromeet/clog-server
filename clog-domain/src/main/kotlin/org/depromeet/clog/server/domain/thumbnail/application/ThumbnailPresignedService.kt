package org.depromeet.clog.server.domain.thumbnail.application

import org.depromeet.clog.server.domain.thumbnail.dto.ThumbnailPresignedUploadRequest
import org.depromeet.clog.server.domain.thumbnail.dto.ThumbnailPresignedUploadResponse

interface ThumbnailPresignedService {
    fun generatePresignedUrl(request: ThumbnailPresignedUploadRequest): ThumbnailPresignedUploadResponse
}
