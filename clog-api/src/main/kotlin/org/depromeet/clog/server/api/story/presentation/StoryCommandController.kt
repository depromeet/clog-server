package org.depromeet.clog.server.api.story.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.depromeet.clog.server.api.configuration.ApiConstants
import org.depromeet.clog.server.api.configuration.annotation.ApiErrorCodes
import org.depromeet.clog.server.api.story.application.DeleteStory
import org.depromeet.clog.server.api.story.application.SaveStory
import org.depromeet.clog.server.api.story.application.UpdateStoryMemo
import org.depromeet.clog.server.api.story.application.UpdateStoryStatus
import org.depromeet.clog.server.api.user.UserContext
import org.depromeet.clog.server.domain.common.ClogApiResponse
import org.depromeet.clog.server.domain.common.ErrorCode.STORY_NOT_FOUND
import org.depromeet.clog.server.domain.story.StoryStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "기록 API", description = "가장 큰 범위의 데이터인 기록에 관한 정보를 다룹니다.")
@RequestMapping("${ApiConstants.API_BASE_PATH_V1}/stories")
@RestController
class StoryCommandController(
    private val saveStory: SaveStory,
    private val deleteStory: DeleteStory,
    private val updateStoryMemo: UpdateStoryMemo,
    private val updateStoryStatus: UpdateStoryStatus
) {

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

    @Operation(
        summary = "기록 삭제",
        description = "기록 삭제에 사용됩니다. 문제와 시도 정보를 함께 삭제합니다.",
    )
    @DeleteMapping("/{storyId}")
    fun delete(
        @PathVariable storyId: Long
    ): ClogApiResponse<Unit> {
        deleteStory(storyId)
        return ClogApiResponse.from(Unit)
    }

    @Operation(
        summary = "기록 메모 수정 API",
        description = "기록 메모 수정에 사용됩니다.",
    )
    @PatchMapping("/{storyId}/memo")
    fun updateMemo(
        userContext: UserContext,
        @PathVariable storyId: Long,
        @RequestBody request: UpdateStoryMemoRequest,
    ): ClogApiResponse<Unit> {
        updateStoryMemo(userContext.userId, storyId, request)
        return ClogApiResponse.from(Unit)
    }

    @ApiErrorCodes([STORY_NOT_FOUND])
    @Operation(
        summary = "기록 상태 수정 API",
        description = "기록 상태 수정에 사용됩니다. 상태가 `DONE`이 된 경우에만 폴더 및 캘린더에 반영됩니다.",
    )
    @PatchMapping("/{storyId}/status/{status}")
    fun updateStatus(
        @PathVariable storyId: Long,
        @PathVariable status: StoryStatus,
    ): ClogApiResponse<Unit> {
        updateStoryStatus(storyId, status)
        return ClogApiResponse.from(Unit)
    }
}
