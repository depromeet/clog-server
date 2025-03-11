package org.depromeet.clog.server.api.story.presentation

data class StorySummaryResponse(
    val id: Long,
    val totalDurationMs: Long,
    val totalAttemptsCount: Int,
    val totalSuccessCount: Int,
    val totalFailCount: Int,
    val problems: List<Problem>,
) {

    data class Problem(
        val id: Long,
        val attemptCount: Int,
        val colorHex: String? = null,
    )
}
