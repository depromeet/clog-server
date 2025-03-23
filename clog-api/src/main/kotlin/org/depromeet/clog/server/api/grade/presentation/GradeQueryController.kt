package org.depromeet.clog.server.api.grade.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.depromeet.clog.server.api.configuration.ApiConstants
import org.depromeet.clog.server.api.grade.application.GetCragGrade
import org.depromeet.clog.server.api.grade.application.GetMyGrade
import org.depromeet.clog.server.api.grade.presentation.dto.GetMyGradeInfoResponse
import org.depromeet.clog.server.api.grade.presentation.dto.GradesResponse
import org.depromeet.clog.server.api.user.UserContext
import org.depromeet.clog.server.domain.common.ClogApiResponse
import org.depromeet.clog.server.global.utils.dto.CursorPagination
import org.springdoc.core.annotations.ParameterObject
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "난이도 조회 API", description = "난이도 정보를 조회할 때 사용하는 API입니다.")
@RequestMapping(ApiConstants.API_BASE_PATH_V1)
@RestController
class GradeQueryController(
    private val getMyGrade: GetMyGrade,
    private val getCragGrade: GetCragGrade
) {

    @Operation(
        summary = "암장에 포함된 난이도 정보 조회",
        description = "암장에 포함된 난이도 정보를 조회합니다."
    )
    @GetMapping("/{cragId}/grades")
    fun getCragGrades(
        @PathVariable("cragId") cragId: Long
    ): ClogApiResponse<GradesResponse> {
        val result = getCragGrade(cragId)
        return ClogApiResponse.from(result)
    }

    @Operation(
        summary = "내 난이도 정보 조회",
        description = "현재 사용자가 기록한 난이도 정보를 조회합니다."
    )
    @GetMapping("/grades/me")
    fun getRecordedGrades(
        userContext: UserContext,
        @ModelAttribute @ParameterObject request: CursorPagination.GeneralRequest
    ): ClogApiResponse<CursorPagination.Response<Long, GetMyGradeInfoResponse>> {
        val pagedResponse =
            getMyGrade.getMyGrades(
                userContext.userId,
                request.cursor,
                request.pageSize
            )
        return ClogApiResponse.from(pagedResponse)
    }
}
