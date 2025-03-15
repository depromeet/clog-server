package org.depromeet.clog.server.domain.attempt

import org.depromeet.clog.server.domain.attempt.dto.AttemptFilter
import org.depromeet.clog.server.domain.attempt.dto.AttemptFolderView

interface AttemptRepository {

    fun save(attempt: Attempt): Attempt
    fun findByIdOrNull(attemptId: Long): Attempt?
    fun deleteById(attemptId: Long)
    fun findAttemptsByUserAndFilter(userId: Long, filter: AttemptFilter): List<AttemptFolderView>
}
