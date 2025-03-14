package org.depromeet.clog.server.api.attempt.application

import org.depromeet.clog.server.api.attempt.presentation.dto.GetAttemptResponse
import org.depromeet.clog.server.api.attempt.presentation.dto.toGetAttemptDetailResponse
import org.depromeet.clog.server.domain.attempt.AttemptRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetAttempt(
    private val attemptRepository: AttemptRepository
) {

    @Transactional(readOnly = true)
    fun getAttemptDetail(
        userId: Long,
        attemptFilter: org.depromeet.clog.server.api.attempt.presentation.dto.AttemptFilter
    ): GetAttemptResponse {
        val attemptFilter = org.depromeet.clog.server.domain.attempt.dto.AttemptFilter(
            attemptStatus = attemptFilter.attemptStatus,
            cragId = attemptFilter.cragId,
            gradeId = attemptFilter.gradeId
        )
        val domainAttempts = attemptRepository.findAttemptsByUserAndFilter(userId, attemptFilter)
        val apiAttempts = domainAttempts.map { it.toGetAttemptDetailResponse() }
        return GetAttemptResponse(apiAttempts)
    }
}
