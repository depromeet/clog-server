package org.depromeet.clog.server.api.story.presentation

import io.swagger.v3.oas.annotations.media.Schema
import org.depromeet.clog.server.api.attempt.presentation.dto.SaveVideoRequest
import java.time.LocalDate

@Schema(description = "갤러리 영상 묶음 기록 요청")
data class SaveGalleryStoryRequest(
    @Schema(description = "기록 날짜", example = "2024-05-25")
    val date: LocalDate,

    @Schema(
        description = "암장 ID(선택). 등록하지 않을 경우 null",
        example = "1",
        nullable = true
    )
    val cragId: Long? = null,

    @Schema(description = "기록 메모", example = "오늘 죽을 뻔함")
    val memo: String? = null,

    @Schema(description = "등록할 영상 목록")
    val videos: List<SaveVideoRequest>
)
