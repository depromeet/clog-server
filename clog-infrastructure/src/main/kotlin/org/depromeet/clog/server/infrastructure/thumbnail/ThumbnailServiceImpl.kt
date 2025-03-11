package org.depromeet.clog.server.infrastructure.thumbnail

import org.depromeet.clog.server.domain.thumbnail.Thumbnail
import org.depromeet.clog.server.domain.thumbnail.ThumbnailRepository
import org.depromeet.clog.server.domain.thumbnail.application.ThumbnailService
import org.depromeet.clog.server.domain.thumbnail.application.ThumbnailUploadResponse
import org.depromeet.clog.server.domain.video.VideoRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class ThumbnailServiceImpl(
    private val thumbnailRepository: ThumbnailRepository,
    private val ncpObjectStorageClient: NcpObjectStorageClient,
    private val videoRepository: VideoRepository
) : ThumbnailService {

    override fun uploadImage(file: MultipartFile, videoId: Long): ThumbnailUploadResponse {
        val fileUrl = ncpObjectStorageClient.uploadFile(file)
        val thumbnail = Thumbnail(id = null, fileUrl = fileUrl)

        thumbnailRepository.save(thumbnail)
        videoRepository.updateThumbnailUrl(videoId, fileUrl)

        return ThumbnailUploadResponse(fileUrl)
    }
}
