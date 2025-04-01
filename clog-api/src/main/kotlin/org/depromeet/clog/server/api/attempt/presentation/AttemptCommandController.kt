package org.depromeet.clog.server.api.attempt.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.depromeet.clog.server.api.attempt.application.DeleteAttemptAndParents
import org.depromeet.clog.server.api.attempt.application.SaveAttempt
import org.depromeet.clog.server.api.attempt.application.UpdateAttemptAndParents
import org.depromeet.clog.server.api.attempt.presentation.dto.SaveAttemptRequest
import org.depromeet.clog.server.api.attempt.presentation.dto.SaveAttemptResponse
import org.depromeet.clog.server.api.attempt.presentation.dto.UpdateAttemptRequest
import org.depromeet.clog.server.api.configuration.ApiConstants
import org.depromeet.clog.server.api.configuration.annotation.ApiErrorCodes
import org.depromeet.clog.server.api.user.UserContext
import org.depromeet.clog.server.domain.common.ClogApiResponse
import org.depromeet.clog.server.domain.common.ErrorCode
import org.springframework.web.bind.annotation.*

@Tag(name = "시도 API", description = "한 시도(촬영)에 관한 정보를 다룹니다.")
@RequestMapping("${ApiConstants.API_BASE_PATH_V1}/attempts")
@RestController
class AttemptCommandController(
    private val saveAttempt: SaveAttempt,
    private val updateAttemptAndParents: UpdateAttemptAndParents,
    private val deleteAttemptAndParents: DeleteAttemptAndParents,
) {

    @Operation(summary = "시도 등록 API", description = "한 영상 촬영이 마무리된 후, 영상 편집까지 마치면 호출하는 API입니다.")
    @PostMapping
    fun registerAttempt(
        @RequestBody @Valid request: SaveAttemptRequest,
        userContext: UserContext
    ): ClogApiResponse<SaveAttemptResponse> {
        val result = saveAttempt(request, userContext.userId)
        return ClogApiResponse.from(result)
    }

    @ApiErrorCodes([ErrorCode.ATTEMPT_NOT_FOUND])
    @Operation(summary = "시도 수정 API", description = "암장 정보 및 난이도 수정 / 완등 실패 여부 수정에 사용합니다.")
    @PatchMapping("/{attemptId}")
    fun update(
        @PathVariable attemptId: Long,
        @RequestBody @Valid request: UpdateAttemptRequest,
    ): ClogApiResponse<Unit> {
        updateAttemptAndParents(attemptId, request)
        return ClogApiResponse.from(Unit)
    }

    @ApiErrorCodes([ErrorCode.ATTEMPT_NOT_FOUND])
    @Operation(
        summary = "시도 삭제 API",
        description = "시도를 삭제합니다. 만약 시도의 부모인 문제 혹은 기록의 아이템이 모두 삭제되었다면, 부모도 삭제됩니다."
    )
    @DeleteMapping("/{attemptId}")
    fun delete(
        @PathVariable attemptId: Long,
    ): ClogApiResponse<Unit> {
        deleteAttemptAndParents(attemptId)
        return ClogApiResponse.from(Unit)
    }
}
