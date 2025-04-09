package org.depromeet.clog.server.domain.thumbnail.dto

data class ThumbnailPresignedUploadRequest(
    val originalFilename: String,
    val contentType: String
)
