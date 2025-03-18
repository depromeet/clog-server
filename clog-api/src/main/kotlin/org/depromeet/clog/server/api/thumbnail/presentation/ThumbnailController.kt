package org.depromeet.clog.server.api.thumbnail.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.depromeet.clog.server.api.configuration.ApiConstants.API_BASE_PATH_V1
import org.depromeet.clog.server.domain.common.ClogApiResponse
import org.depromeet.clog.server.domain.thumbnail.application.ThumbnailService
import org.depromeet.clog.server.domain.thumbnail.application.ThumbnailUploadResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@Tag(name = "썸네일 API", description = "썸네일 관련 작업을 수행합니다.")
@RestController
@RequestMapping("$API_BASE_PATH_V1/thumbnails")
class ThumbnailController(
    private val thumbnailService: ThumbnailService
) {

    @Operation(summary = "썸네일 업로드")
    @PostMapping("/upload")
    fun uploadImage(
        @RequestParam("file")
        file: MultipartFile
    ): ClogApiResponse<ThumbnailUploadResponse> {
        val response = thumbnailService.uploadImage(file)
        return ClogApiResponse.from(response)
    }
}
