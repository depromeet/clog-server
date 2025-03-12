package org.depromeet.clog.server.api.story.presentation

import org.depromeet.clog.server.api.attempt.presentation.dto.AttemptRequest
import org.depromeet.clog.server.api.problem.presentation.ProblemRequest
import org.depromeet.clog.server.domain.story.Story
import java.time.LocalDate

data class SaveStoryRequest(

    val cragId: Long? = null,

    val problem: ProblemRequest,

    val attempt: AttemptRequest,
) {

    fun toDomain(userId: Long): Story {
        return Story(
            userId = userId,
            cragId = cragId,
            date = LocalDate.now(),
        )
    }
}
