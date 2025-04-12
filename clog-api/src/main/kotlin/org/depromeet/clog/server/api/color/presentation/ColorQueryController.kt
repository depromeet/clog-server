package org.depromeet.clog.server.api.color.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.depromeet.clog.server.api.color.application.GetMyColors
import org.depromeet.clog.server.api.configuration.ApiConstants
import org.depromeet.clog.server.api.crag.presentation.dto.GetMyColorResponse
import org.depromeet.clog.server.api.user.UserContext
import org.depromeet.clog.server.domain.common.ClogApiResponse
import org.depromeet.clog.server.global.utils.dto.CursorPagination
import org.springdoc.core.annotations.ParameterObject
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "색상 조회 API", description = "색상 정보를 조회할 때 사용하는 API입니다.")
@RequestMapping(ApiConstants.API_BASE_PATH_V1)
@RestController
class ColorQueryController(
    private val getMyColors: GetMyColors,
) {

    @Operation(
        summary = "내 색상 정보 조회",
        description = "사용자가 기록한 모든 영상의 유니크한 색상 정보를 조회합니다."
    )
    @GetMapping("/colors/me")
    fun getRecordedGrades(
        userContext: UserContext,
        @ModelAttribute @ParameterObject request: CursorPagination.GeneralRequest
    ): ClogApiResponse<CursorPagination.Response<Long, GetMyColorResponse>> {
        val result = getMyColors(
            userContext.userId,
            request.cursor,
            request.pageSize,
        )
        return ClogApiResponse.from(result)
    }
}
