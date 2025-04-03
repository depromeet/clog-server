package org.depromeet.clog.server.api.story.presentation

data class SaveStoryResponse(
    val storyId: Long,
    val problemId: Long,
    val attemptId: Long,
)
