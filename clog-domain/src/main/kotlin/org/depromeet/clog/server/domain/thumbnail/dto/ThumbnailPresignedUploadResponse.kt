package org.depromeet.clog.server.domain.thumbnail.dto

data class ThumbnailPresignedUploadResponse(
    val presignedUrl: String,
    val fileUrl: String
)
