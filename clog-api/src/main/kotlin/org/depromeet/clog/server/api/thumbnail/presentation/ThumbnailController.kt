package org.depromeet.clog.server.api.thumbnail.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.depromeet.clog.server.api.configuration.ApiConstants.API_BASE_PATH_V1
import org.depromeet.clog.server.domain.common.ClogApiResponse
import org.depromeet.clog.server.domain.thumbnail.application.ThumbnailPresignedService
import org.depromeet.clog.server.domain.thumbnail.application.ThumbnailService
import org.depromeet.clog.server.domain.thumbnail.application.ThumbnailUploadResponse
import org.depromeet.clog.server.domain.thumbnail.dto.ThumbnailPresignedUploadRequest
import org.depromeet.clog.server.domain.thumbnail.dto.ThumbnailPresignedUploadResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@Tag(name = "썸네일 API", description = "썸네일 관련 작업을 수행합니다.")
@RestController
@RequestMapping("$API_BASE_PATH_V1/thumbnails")
class ThumbnailController(
    private val thumbnailService: ThumbnailService,
    private val thumbnailPresignedService: ThumbnailPresignedService
) {

    @Operation(summary = "썸네일 업로드")
    @PostMapping("/upload", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadImage(
        @Parameter(description = "썸네일 이미지 파일을 key=`file`로 보내주세요.")
        @RequestPart("file")
        file: MultipartFile
    ): ClogApiResponse<ThumbnailUploadResponse> {
        val response = thumbnailService.uploadImage(file)
        return ClogApiResponse.from(response)
    }

    @Operation(summary = "썸네일 presigned URL 발급")
    @PostMapping("/upload-url")
    fun getPresignedUrl(
        @RequestBody request: ThumbnailPresignedUploadRequest
    ): ClogApiResponse<ThumbnailPresignedUploadResponse> {
        val response = thumbnailPresignedService.generatePresignedUrl(request)
        return ClogApiResponse.from(response)
    }
}
