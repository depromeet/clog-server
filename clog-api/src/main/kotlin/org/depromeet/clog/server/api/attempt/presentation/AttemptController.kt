package org.depromeet.clog.server.api.attempt.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.depromeet.clog.server.api.attempt.application.SaveAttempt
import org.depromeet.clog.server.api.configuration.ApiConstants
import org.depromeet.clog.server.domain.common.ClogApiResponse
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "시도 API", description = "한 시도(촬영)에 관한 정보를 다룹니다.")
@RequestMapping("${ApiConstants.API_BASE_PATH_V1}/problems/{problemId}/attempts")
@RestController
class AttemptController(
    private val saveAttempt: SaveAttempt,
) {

    @Operation(summary = "시도 등록 API")
    @PostMapping()
    fun registerAttempt(
        @PathVariable problemId: Long,
        @RequestBody @Valid request: AttemptRequest,
    ): ClogApiResponse<AttemptResponse> {
        val result = saveAttempt(problemId, request)
        return ClogApiResponse.from(result)
    }
}
