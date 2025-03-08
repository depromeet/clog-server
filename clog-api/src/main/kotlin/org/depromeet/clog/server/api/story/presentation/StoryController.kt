package org.depromeet.clog.server.api.story.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.depromeet.clog.server.api.configuration.ApiConstants
import org.depromeet.clog.server.api.story.application.GetStory
import org.depromeet.clog.server.api.story.application.SaveStory
import org.depromeet.clog.server.api.user.UserContext
import org.depromeet.clog.server.domain.common.ClogApiResponse
import org.springframework.web.bind.annotation.*

@Tag(name = "기록 API")
@RequestMapping("${ApiConstants.API_BASE_PATH_V1}/stories")
@RestController
class StoryController(
    private val getStory: GetStory,
    private val saveStory: SaveStory,
) {

    @GetMapping("/{storyId}")
    fun get(@PathVariable storyId: Long): ClogApiResponse<StoryResponse> {
        val result = getStory(storyId)
        return ClogApiResponse.from(result)
    }

    @Operation(
        summary = "기록 저장",
        description = "기록 최초 저장에 사용됩니다. 첫 번째 시도 종료 후 사용되며, 문제와 시도 정보를 함께 저장합니다.",
    )
    @PostMapping
    fun save(
        userContext: UserContext,
        @RequestBody request: SaveStoryRequest,
    ): ClogApiResponse<SaveStoryResponse> {
        val result = saveStory(userContext.userId, request)
        return ClogApiResponse.from(result)
    }
}
