package org.depromeet.clog.server.domain.story

import org.depromeet.clog.server.domain.attempt.AttemptQuery
import org.depromeet.clog.server.domain.crag.domain.Crag
import org.depromeet.clog.server.domain.problem.ProblemQuery
import java.time.LocalDate

data class StoryQuery(
    val id: Long,
    val userId: Long? = null,
    val crag: Crag? = null,
    val memo: String? = null,
    val date: LocalDate,
    val problems: List<ProblemQuery>,
) {
    val totalDurationMs: Long
        get() = problems.sumOf { it.attempts.sumOf { at -> at.video.durationMs } }

    val attempts: List<AttemptQuery>
        get() = problems.flatMap { it.attempts }
}

fun List<StoryQuery>.getRandomThumbnailUrl(): String? {
    return this.flatMap { it.attempts }
        .map { it.video.thumbnailUrl }
        .randomOrNull()
}
