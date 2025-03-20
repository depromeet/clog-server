package org.depromeet.clog.server.api.report.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.depromeet.clog.server.api.configuration.ApiConstants
import org.depromeet.clog.server.api.report.application.GetReport
import org.depromeet.clog.server.api.report.presentation.dto.DetailedReportResponse
import org.depromeet.clog.server.api.report.presentation.dto.ReportResponse
import org.depromeet.clog.server.api.user.UserContext
import org.depromeet.clog.server.domain.common.ClogApiResponse
import org.springframework.web.bind.annotation.GetMapping
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
        description = "최근 3개월 내 시도 횟수, 총 운동 시간, 시도 건수, 완등 건수, 완등률, 사용자 닉네임을 제공합니다."
    )
    @GetMapping
    fun getReport(userContext: UserContext): ClogApiResponse<ReportResponse> {
        val report = getReport.getMyReport(userContext.userId)
        return ClogApiResponse.from(report)
    }

    @Operation(
        summary = "사용자 리포트 통계 조회",
        description = "가장 많이 도전한 문제(암장, 난이도, 도전 횟수 및 시도 영상)와 가장 많이 방문한 암장 정보를 제공합니다."
    )
    @GetMapping("/statistics")
    fun getStatisticsReport(userContext: UserContext): ClogApiResponse<DetailedReportResponse> {
        val report = getReport.getReportStatistic(userContext.userId)
        return ClogApiResponse.from(report)
    }
}
