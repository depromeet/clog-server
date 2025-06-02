package org.depromeet.clog.server.api.story.presentation

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "갤러리 영상 묶음 기록 저장 응답")
data class SaveGalleryStoryResponse(
    @Schema(description = "기록 ID", example = "1")
    val storyId: Long
)
