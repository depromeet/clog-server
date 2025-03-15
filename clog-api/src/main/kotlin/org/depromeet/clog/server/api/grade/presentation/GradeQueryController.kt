package org.depromeet.clog.server.api.grade.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.depromeet.clog.server.api.configuration.ApiConstants
import org.depromeet.clog.server.api.grade.application.GetMyGrade
import org.depromeet.clog.server.api.grade.presentation.dto.GetMyGradeInfoResponse
import org.depromeet.clog.server.api.user.UserContext
import org.depromeet.clog.server.domain.common.ClogApiResponse
import org.depromeet.clog.server.global.utils.dto.CursorPageRequest
import org.depromeet.clog.server.global.utils.dto.PagedResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "난이도 조회 API", description = "난이도 정보를 조회할 때 사용하는 API입니다.")
@RequestMapping("${ApiConstants.API_BASE_PATH_V1}/grades")
@RestController
class GradeQueryController(
    private val getMyGrade: GetMyGrade
) {
    @Operation(
        summary = "내 난이도 정보 조회",
        description = "현재 사용자가 기록한 난이도 정보를 cursor 기반 페이지네이션으로 조회합니다. (gradeId, colorName, colorHex)"
    )
    @GetMapping("/me")
    fun getRecordedGrades(
        userContext: UserContext,
        @ModelAttribute pageRequest: CursorPageRequest
    ): ClogApiResponse<PagedResponse<GetMyGradeInfoResponse>> {
        val pagedResponse: PagedResponse<GetMyGradeInfoResponse> =
            getMyGrade.getRecordedGrades(
                userContext.userId,
                pageRequest.cursor,
                pageRequest.pageSize
            )
        return ClogApiResponse.from(pagedResponse)
    }
}
