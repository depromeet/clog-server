package org.depromeet.clog.server.api.attempt.application

import org.depromeet.clog.server.api.attempt.presentation.AttemptRequest
import org.depromeet.clog.server.api.attempt.presentation.SaveAttemptResponse
import org.depromeet.clog.server.domain.attempt.AttemptRepository
import org.depromeet.clog.server.domain.video.VideoRepository
import org.depromeet.clog.server.domain.video.VideoStampRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SaveAttempt(
    private val attemptRepository: AttemptRepository,
    private val videoRepository: VideoRepository,
    private val videoStampRepository: VideoStampRepository,
) {

    @Transactional
    operator fun invoke(problemId: Long, request: AttemptRequest): SaveAttemptResponse {
        val video = videoRepository.save(request.video.toDomain())
        videoStampRepository.saveAll(
            request.video.stamps.map { it.toDomain(video.id!!) }
        )
        val attempt = attemptRepository.save(
            request.toDomain(problemId, video.id!!)
        )

        return SaveAttemptResponse(attempt.id!!)
    }
}
