package org.depromeet.clog.server.api.crag.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.depromeet.clog.server.api.configuration.ApiConstants
import org.depromeet.clog.server.api.crag.application.GetMyCrag
import org.depromeet.clog.server.api.crag.presentation.dto.CragResponse
import org.depromeet.clog.server.api.user.UserContext
import org.depromeet.clog.server.domain.common.ClogApiResponse
import org.depromeet.clog.server.global.utils.dto.CursorPagination.Request
import org.depromeet.clog.server.global.utils.dto.CursorPagination.Response
import org.springdoc.core.annotations.ParameterObject
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "암장 조회 API", description = "암장 정보를 조회할때 사용하는 API 입니다.")
@RequestMapping("${ApiConstants.API_BASE_PATH_V1}/crags")
@RestController
class CragQueryController(
    private val getMyCrag: GetMyCrag
) {

    @Operation(
        summary = "내 암장 정보 조회",
        description = "현재 사용자가 기록한 암장 정보를 조회합니다."
    )
    @GetMapping("/me")
    fun getRecordedCrags(
        userContext: UserContext,
        @ModelAttribute @ParameterObject pageRequest: Request
    ): ClogApiResponse<Response<CragResponse>> {
        val pagedResponse =
            getMyCrag.getMyCrags(userContext.userId, pageRequest.cursor, pageRequest.pageSize)
        return ClogApiResponse.from(pagedResponse)
    }
}
