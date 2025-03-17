package org.depromeet.clog.server.domain.attempt

import org.depromeet.clog.server.domain.attempt.dto.AttemptFilter
import org.depromeet.clog.server.domain.attempt.dto.AttemptFolderView

interface AttemptRepository {

    fun save(attempt: AttemptCommand): AttemptQuery

    fun findAttemptsByUserAndFilter(userId: Long, filter: AttemptFilter): List<AttemptFolderView>

    fun deleteById(attemptId: Long)
}
