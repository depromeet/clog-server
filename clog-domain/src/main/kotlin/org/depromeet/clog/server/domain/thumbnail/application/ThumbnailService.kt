package org.depromeet.clog.server.domain.thumbnail.application

import jakarta.transaction.Transactional
import org.springframework.web.multipart.MultipartFile

@Transactional
interface ThumbnailService {
    fun uploadImage(file: MultipartFile, videoId: Long): ThumbnailUploadResponse
}
