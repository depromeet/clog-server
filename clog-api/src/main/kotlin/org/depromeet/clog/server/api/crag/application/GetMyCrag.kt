package org.depromeet.clog.server.api.crag.application

import org.depromeet.clog.server.api.crag.presentation.dto.GetMyCragInfoResponse
import org.depromeet.clog.server.api.crag.presentation.dto.toGetMyCragInfoResponse
import org.depromeet.clog.server.domain.crag.domain.Crag
import org.depromeet.clog.server.domain.story.StoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetMyCrag(
    private val storyRepository: StoryRepository
) {
    @Transactional(readOnly = true)
    fun getRecordedCrags(userId: Long): List<GetMyCragInfoResponse> {
        val domainCrags: List<Crag> = storyRepository.findDistinctCragsByUserId(userId)
        return domainCrags.map { it.toGetMyCragInfoResponse() }
    }
}
