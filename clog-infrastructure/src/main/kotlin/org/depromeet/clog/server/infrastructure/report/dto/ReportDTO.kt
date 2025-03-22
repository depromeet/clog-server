package org.depromeet.clog.server.infrastructure.report.dto

import org.depromeet.clog.server.infrastructure.problem.ProblemEntity
import org.depromeet.clog.server.infrastructure.story.StoryEntity

data class ReportDTO(
    val story: StoryEntity,
    val problem: ProblemEntity
)
