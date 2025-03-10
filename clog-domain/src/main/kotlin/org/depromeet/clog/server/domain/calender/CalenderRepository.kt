package org.depromeet.clog.server.domain.calender

import org.depromeet.clog.server.domain.video.Video

interface CalenderRepository {

    fun getCalender(
        userId: Long,
        year: Int,
        month: Int,
    ): Calender

    fun calculateTotalClimbTime(
        userId: Long,
        year: Int,
        month: Int,
    ): Int

    fun findRandomVideoBy(storyId: Long): Video?
}
