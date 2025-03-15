package org.depromeet.clog.server.api.calendar.controller

import io.swagger.v3.oas.annotations.Parameter
import org.springdoc.core.annotations.ParameterObject

@ParameterObject
data class CalendarQuery(

    @Parameter(description = "조회할 년도", example = "2023", required = true)
    val year: Int,

    @Parameter(description = "조회할 월", example = "10", required = true)
    val month: Int,
)
