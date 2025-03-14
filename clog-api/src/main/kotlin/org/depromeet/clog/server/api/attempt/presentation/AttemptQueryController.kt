package org.depromeet.clog.server.api.attempt.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.depromeet.clog.server.api.attempt.application.GetAttempt
import org.depromeet.clog.server.api.attempt.presentation.dto.AttemptFilterRequest
import org.depromeet.clog.server.api.attempt.presentation.dto.GetAttemptResponse
import org.depromeet.clog.server.api.configuration.ApiConstants
import org.depromeet.clog.server.api.user.UserContext
import org.depromeet.clog.server.domain.common.ClogApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "폴더 API", description = "폴더(나의 클라이밍 기록)에 쓰이는 API입니다.")
@RequestMapping("${ApiConstants.API_BASE_PATH_V1}/folders")
@RestController
class AttemptQueryController(
    private val getAttempt: GetAttempt,
) {
    @Operation(
        summary = "폴더 조회",
        description = "나의 클라이밍 기록 조회 (필터: 시도 상태, 암장ID, 난이도ID)"
    )
    @GetMapping
    fun getFolder(
        userContext: UserContext,
        @ModelAttribute filter: AttemptFilterRequest
    ): ClogApiResponse<GetAttemptResponse> {
        val result = getAttempt.getAttemptDetail(userContext.userId, filter)
        return ClogApiResponse.from(result)
    }
}
