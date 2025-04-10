package org.depromeet.clog.server.api.calendar.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.depromeet.clog.server.api.calendar.application.CalendarResponse
import org.depromeet.clog.server.api.calendar.application.GetCalendar
import org.depromeet.clog.server.api.configuration.ApiConstants
import org.depromeet.clog.server.api.user.UserContext
import org.depromeet.clog.server.domain.common.ClogApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "캘린더 조회 API", description = "캘린더 관련 정보를 조회합니다.")
@RequestMapping("${ApiConstants.API_BASE_PATH_V1}/calendars")
@RestController
class CalendarController(
    private val getCalendar: GetCalendar,
) {

    @Operation(
        summary = "특정 년/월 캘린더 정보 조회",
        description = "특정 년/월의 캘린더 메인 화면에 보여져야 하는 정보를 조회합니다.",
    )
    @GetMapping
    fun getAll(
        userContext: UserContext,
        @ModelAttribute query: CalendarQuery,
    ): ClogApiResponse<CalendarResponse> {
        val result = getCalendar(
            userId = userContext.userId,
            year = query.year,
            month = query.month,
        )
        return ClogApiResponse.from(result)
    }
}
