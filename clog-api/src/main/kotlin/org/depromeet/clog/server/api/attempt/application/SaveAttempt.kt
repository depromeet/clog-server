package org.depromeet.clog.server.api.attempt.application

import jakarta.transaction.Transactional
import org.depromeet.clog.server.api.attempt.presentation.AttemptRequest
import org.depromeet.clog.server.api.attempt.presentation.AttemptResponse
import org.depromeet.clog.server.domain.attempt.AttemptRepository
import org.depromeet.clog.server.domain.video.VideoRepository
import org.depromeet.clog.server.domain.video.VideoStampRepository
import org.springframework.stereotype.Service

@Service
class SaveAttempt(
    private val attemptRepository: AttemptRepository,
    private val videoRepository: VideoRepository,
    private val videoStampRepository: VideoStampRepository,
) {

    @Transactional
    operator fun invoke(problemId: Long, request: AttemptRequest): AttemptResponse {
        val video = videoRepository.save(request.video.toDomain())
        videoStampRepository.saveAll(
            request.video.stamps.map { it.toDomain(video.id!!) }
        )
        val attempt = attemptRepository.save(
            request.toDomain(problemId, video.id!!)
        )

        return AttemptResponse(attempt.id!!)
    }
}
