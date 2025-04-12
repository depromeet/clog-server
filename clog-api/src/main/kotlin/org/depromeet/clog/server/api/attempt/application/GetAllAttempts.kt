package org.depromeet.clog.server.api.attempt.application

import org.depromeet.clog.server.api.attempt.presentation.dto.AttemptFilterRequest
import org.depromeet.clog.server.api.attempt.presentation.dto.GetMyAttemptsResponse
import org.depromeet.clog.server.api.attempt.presentation.dto.toGetAttemptDetailResponse
import org.depromeet.clog.server.domain.attempt.AttemptRepository
import org.depromeet.clog.server.domain.attempt.dto.AttemptFilter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetAllAttempts(
    private val attemptRepository: AttemptRepository
) {

    @Transactional(readOnly = true)
    fun getAttemptDetail(
        userId: Long,
        request: AttemptFilterRequest,
    ): GetMyAttemptsResponse {
        val attemptFilter = AttemptFilter(
            attemptStatus = request.attemptStatus,
            cragId = request.cragId,
            colorId = request.colorId ?: request.gradeId,
        )
        val domainAttempts = attemptRepository.findAttemptsByUserAndFilter(userId, attemptFilter)
        val apiAttempts = domainAttempts.map { it.toGetAttemptDetailResponse() }
        return GetMyAttemptsResponse(apiAttempts)
    }
}
