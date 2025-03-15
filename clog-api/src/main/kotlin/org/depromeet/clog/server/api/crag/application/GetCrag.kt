package org.depromeet.clog.server.api.crag.application

import org.depromeet.clog.server.api.crag.presentation.dto.GetMyCragInfoResponse
import org.depromeet.clog.server.domain.story.StoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetCrag(
    private val storyRepository: StoryRepository
) {
    @Transactional(readOnly = true)
    fun getRecordedCrags(userId: Long): List<GetMyCragInfoResponse> {
        val domainCrags = storyRepository.findDistinctCragsByUserId(userId)
        return domainCrags.map { GetMyCragInfoResponse(it.id, it.name, it.roadAddress) }
    }
}
