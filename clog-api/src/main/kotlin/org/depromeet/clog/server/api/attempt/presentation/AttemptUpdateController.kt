package org.depromeet.clog.server.api.attempt.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.depromeet.clog.server.api.attempt.application.UpdateAttemptAndParents
import org.depromeet.clog.server.api.configuration.ApiConstants
import org.depromeet.clog.server.domain.common.ClogApiResponse
import org.springframework.web.bind.annotation.*

@Tag(name = "시도 API", description = "한 시도(촬영)에 관한 정보를 다룹니다.")
@RequestMapping("${ApiConstants.API_BASE_PATH_V1}/attempts")
@RestController
class AttemptUpdateController(
    private val updateAttemptAndParents: UpdateAttemptAndParents,
) {

    @Operation(summary = "시도 수정 API", description = "암장 정보 및 난이도 수정 / 완등 실패 여부 수정에 사용합니다.")
    @PatchMapping("/{attemptId}")
    fun update(
        @PathVariable attemptId: Long,
        @RequestBody @Valid request: AttemptUpdateRequest,
    ): ClogApiResponse<Unit> {
        updateAttemptAndParents(attemptId, request)
        return ClogApiResponse.from(Unit)
    }
}
