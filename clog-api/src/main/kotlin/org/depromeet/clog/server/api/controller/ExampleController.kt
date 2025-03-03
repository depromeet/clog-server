package org.depromeet.clog.server.api.controller

import org.depromeet.clog.server.api.configuration.ApiConstants.API_BASE_PATH_V1
import org.depromeet.clog.server.domain.common.ApiResponse
import org.depromeet.clog.server.domain.example.GetExample
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Deprecated("예시용으로, 곧 삭제될 예정입니다. (25.02.19 / kkjsw17)")
@Profile("local")
@RestController
@RequestMapping("$API_BASE_PATH_V1/examples")
class ExampleController(
    private val getExample: GetExample,
) {

    @GetMapping
    fun getExamples(
        @RequestParam(required = false, defaultValue = "false") error: Boolean,
    ): ApiResponse<String> {
        val response = getExample(error)
        return ApiResponse.from(response)
    }
}
