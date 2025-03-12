package org.depromeet.clog.server.api.video.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.depromeet.clog.server.api.configuration.ApiConstants
import org.depromeet.clog.server.api.configuration.annotation.ApiErrorCodes
import org.depromeet.clog.server.api.video.application.UpdateVideo
import org.depromeet.clog.server.domain.common.ClogApiResponse
import org.depromeet.clog.server.domain.common.ErrorCode
import org.springframework.web.bind.annotation.*

@Tag(name = "영상 API", description = "영상 정보에 관련된 API 목록입니다.")
@RequestMapping("${ApiConstants.API_BASE_PATH_V1}/videos")
@RestController
class VideoController(
    private val updateVideo: UpdateVideo,
) {

    @ApiErrorCodes([ErrorCode.VIDEO_NOT_FOUND])
    @Operation(summary = "영상 수정 API", description = "영상 정보를 수정합니다.")
    @PatchMapping("/{videoId}")
    fun update(
        @PathVariable videoId: Long,
        @RequestBody @Valid request: VideoUpdateRequest
    ): ClogApiResponse<Unit> {
        updateVideo(videoId, request)
        return ClogApiResponse.from(Unit)
    }
}
