package org.depromeet.clog.server.api.attempt.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.depromeet.clog.server.api.attempt.application.GetAllAttempts
import org.depromeet.clog.server.api.attempt.application.GetAttempt
import org.depromeet.clog.server.api.attempt.presentation.dto.AttemptFilterRequest
import org.depromeet.clog.server.api.attempt.presentation.dto.GetAttemptResponse
import org.depromeet.clog.server.api.attempt.presentation.dto.GetMyAttemptsResponse
import org.depromeet.clog.server.api.configuration.ApiConstants
import org.depromeet.clog.server.api.user.UserContext
import org.depromeet.clog.server.domain.common.ClogApiResponse
import org.springdoc.core.annotations.ParameterObject
import org.springframework.web.bind.annotation.*

@Tag(name = "시도 API", description = "한 시도(촬영)에 관한 정보를 다룹니다.")
@RequestMapping("${ApiConstants.API_BASE_PATH_V1}/attempts")
@RestController
class AttemptQueryController(
    private val getAllAttempts: GetAllAttempts,
    private val getAttempt: GetAttempt,
) {
    @Operation(
        summary = "필터링 기반 시도 조회",
        description = "폴더 뷰 -> 나의 클라이밍 기록 목록 조회에 사용됩니다. (필터: 시도 상태, 암장ID, 난이도ID)"
    )
    @GetMapping
    fun getFolder(
        userContext: UserContext,
        @ModelAttribute @ParameterObject filter: AttemptFilterRequest
    ): ClogApiResponse<GetMyAttemptsResponse> {
        val result = getAllAttempts.getAttemptDetail(userContext.userId, filter)
        return ClogApiResponse.from(result)
    }

    @Operation(
        summary = "시도 상세 조회",
        description = "각 시도 별 상세 화면에 사용됩니다."
    )
    @GetMapping("/{attemptId}")
    fun getAttemptDetail(
        userContext: UserContext,
        @PathVariable attemptId: Long,
    ): ClogApiResponse<GetAttemptResponse> {
        val result = getAttempt(attemptId, userContext.userId)
        return ClogApiResponse.from(result)
    }
}
