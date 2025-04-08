package org.depromeet.clog.server.infrastructure.thumbnail.s3

import org.depromeet.clog.server.domain.thumbnail.Thumbnail
import org.depromeet.clog.server.domain.thumbnail.ThumbnailRepository
import org.depromeet.clog.server.domain.thumbnail.application.*
import org.depromeet.clog.server.domain.thumbnail.dto.ThumbnailPresignedUploadRequest
import org.depromeet.clog.server.domain.thumbnail.dto.ThumbnailPresignedUploadResponse
import org.springframework.stereotype.Service
import java.util.*

@Service
class ThumbnailPresignedServiceImpl(
    private val awsS3PresignedClient: AwsS3PresignedClient,
    private val thumbnailRepository: ThumbnailRepository
) : ThumbnailPresignedService {

    override fun generatePresignedUrl(request: ThumbnailPresignedUploadRequest): ThumbnailPresignedUploadResponse {
        val key = "${UUID.randomUUID()}-${request.originalFilename}"
        val presignedUrl = awsS3PresignedClient.generatePresignedPutUrl(key, request.contentType)
        val fileUrl = awsS3PresignedClient.getCloudFrontUrl(key)

        val thumbnail = Thumbnail(fileUrl = fileUrl)
        thumbnailRepository.save(thumbnail)

        return ThumbnailPresignedUploadResponse(presignedUrl = presignedUrl, fileUrl = fileUrl)
    }
}
