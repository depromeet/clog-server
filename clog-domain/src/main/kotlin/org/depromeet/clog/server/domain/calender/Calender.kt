package org.depromeet.clog.server.domain.calender

import java.time.LocalDate

data class Calender(
    val numOfClimbDays: Int,
    val totalClimbTime: Int,
    val days: List<Day>,
) {

    data class Day(
        val date: LocalDate,
        val storyIds: List<Long>,
        val thumbnailUrl: String? = null,
    )
}
