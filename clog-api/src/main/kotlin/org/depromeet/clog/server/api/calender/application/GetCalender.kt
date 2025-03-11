package org.depromeet.clog.server.api.calender.application

import org.depromeet.clog.server.domain.calender.Calender
import org.depromeet.clog.server.domain.calender.CalenderRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetCalender(
    private val calenderRepository: CalenderRepository,
) {

    @Transactional(readOnly = true)
    operator fun invoke(
        userId: Long,
        year: Int,
        month: Int,
    ): Calender {
        return calenderRepository.getCalender(userId, year, month)
    }
}
