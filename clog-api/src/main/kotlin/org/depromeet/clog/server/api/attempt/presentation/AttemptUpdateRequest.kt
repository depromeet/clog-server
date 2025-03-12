package org.depromeet.clog.server.api.attempt.presentation

import io.swagger.v3.oas.annotations.media.Schema
import org.depromeet.clog.server.domain.attempt.AttemptStatus

@Schema(description = "시도 수정 요청 DTO")
data class AttemptUpdateRequest(

    @Schema(description = "암장 ID", example = "1")
    val cragId: Long? = null,

    @Schema(description = "난이도 ID", example = "1")
    val gradeId: Long? = null,

    @Schema(description = "난이도 미등록으로 변경", example = "true")
    val gradeUnregistered: Boolean? = null,

    @Schema(description = "완등 여부", example = "FAILURE")
    val status: AttemptStatus? = null,
)
