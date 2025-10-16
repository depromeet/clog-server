package org.depromeet.clog.server.api.report.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.depromeet.clog.server.api.configuration.ApiConstants
import org.depromeet.clog.server.api.report.application.GetReport
import org.depromeet.clog.server.api.report.presentation.dto.ReportResponse
import org.depromeet.clog.server.api.user.UserContext
import org.depromeet.clog.server.domain.common.ClogApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "리포트 API", description = "사용자 리포트 정보를 제공합니다.")
@RequestMapping("${ApiConstants.API_BASE_PATH_V1}/reports")
@RestController
class ReportQueryController(
    private val getReport: GetReport
) {
    @Operation(
        summary = "사용자 리포트 조회",
        description = "사용자 리포트를 조회합니다."
    )
    @GetMapping
    fun getReport(userContext: UserContext): ClogApiResponse<ReportResponse> {
        val report = getReport.getMyReport(userContext.userId)
        return ClogApiResponse.from(report)
    }

    @Operation(
        summary = "특정 사용자 리포트 조회 (친구 리포트 조회용)",
        description = "userId를 기준으로 특정 사용자의 리포트를 조회합니다."
    )
    @GetMapping("/{userId}")
    fun getReportByUserId(@PathVariable userId: Long): ClogApiResponse<ReportResponse> {
        val report = getReport.getMyReport(userId)
        return ClogApiResponse.from(report)
    }
}
