package org.depromeet.clog.server.domain.thumbnail.application

import org.springframework.web.multipart.MultipartFile

interface ThumbnailService {
    fun uploadImage(file: MultipartFile, videoId: Long): ThumbnailUploadResponse
}
