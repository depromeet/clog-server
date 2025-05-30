package org.depromeet.clog.server.api.story.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.depromeet.clog.server.api.configuration.ApiConstants
import org.depromeet.clog.server.api.story.application.SaveGalleryStory
import org.depromeet.clog.server.api.user.UserContext
import org.depromeet.clog.server.domain.common.ClogApiResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "기록 API", description = "갤러리 영상 묶음 기록 등록 API")
@RequestMapping("${ApiConstants.API_BASE_PATH_V1}/stories")
@RestController
class GalleryStoryCommandController(
    private val saveGalleryStory: SaveGalleryStory
) {
    @Operation(
        summary = "갤러리에서 여러 영상을 묶어 기록 저장",
        description = "암장 이름, 날짜, 메모와 함께 여러 개의 영상을 기록으로 묶어 저장합니다. 저장된 기록은 폴더/캘린더에 반영됩니다."
    )
    @PostMapping("/gallery")
    fun register(
        userContext: UserContext,
        @RequestBody request: SaveGalleryStoryRequest
    ): ClogApiResponse<SaveGalleryStoryResponse> {
        val result = saveGalleryStory(userContext.userId, request)
        return ClogApiResponse.from(result)
    }
}
