package org.depromeet.clog.server.api.story.presentation

import io.swagger.v3.oas.annotations.media.Schema
import org.depromeet.clog.server.api.attempt.presentation.dto.SaveAttemptRequest
import org.depromeet.clog.server.api.problem.presentation.SaveProblemRequest
import org.depromeet.clog.server.domain.story.StoryCommand
import org.depromeet.clog.server.domain.story.StoryStatus
import java.time.LocalDate

@Schema(description = "새로운 기록 저장 요청")
data class SaveStoryRequest(
    @Schema(description = "암장 ID", example = "1")
    val cragId: Long? = null,

    @Schema(description = "문제 정보")
    val problem: SaveProblemRequest,

    @Schema(description = "시도 정보")
    val attempt: SaveAttemptRequest,

    @Schema(description = "해당 기록에 대한 메모")
    val memo: String? = null,
) {

    fun toDomain(userId: Long, appVersion: String?): StoryCommand {
        val status = if (appVersion != null && appVersion in setOf("1.0.0", "1.0.1", "1.0.2")) {
            StoryStatus.DONE
        } else {
            StoryStatus.IN_PROGRESS
        }

        return StoryCommand(
            userId = userId,
            cragId = cragId,
            date = LocalDate.now(),
            memo = memo,
            status = status,
        )
    }
}
