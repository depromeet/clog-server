package org.depromeet.clog.server.api.attempt.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.depromeet.clog.server.domain.attempt.AttemptCommand
import org.depromeet.clog.server.domain.attempt.AttemptStatus

@Schema(description = "새로 등록할 시도에 대한 요청 정보")
data class SaveAttemptRequest(
    @Schema(description = "시도 상태", example = "SUCCESS")
    val status: AttemptStatus,

    @Schema(description = "문제 ID로, 기록 저장에 사용될 시 null로 세팅", example = "1")
    val problemId: Long? = null,

    @Schema(description = "비디오 요청 정보")
    val video: SaveVideoRequest,
) {
    fun toDomain(videoId: Long): AttemptCommand {
        return AttemptCommand(
            status = status,
            problemId = problemId ?: throw IllegalStateException("problemId is null"),
            videoId = videoId,
        )
    }

    fun toDomain(videoId: Long, problemId: Long): AttemptCommand {
        return AttemptCommand(
            status = status,
            problemId = problemId,
            videoId = videoId,
        )
    }
}
