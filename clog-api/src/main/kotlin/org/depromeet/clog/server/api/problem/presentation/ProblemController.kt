package org.depromeet.clog.server.api.problem.presentation

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.depromeet.clog.server.api.configuration.ApiConstants
import org.depromeet.clog.server.api.problem.application.SaveProblem
import org.depromeet.clog.server.domain.common.ClogApiResponse
import org.springframework.web.bind.annotation.*

@Tag(name = "문제 API")
@RequestMapping("${ApiConstants.API_BASE_PATH_V1}/stories/{storyId}/problems")
@RestController
class ProblemController(
    private val saveProblem: SaveProblem,
) {

    @Operation(summary = "문제 등록", description = "다음 문제 버튼 클릭 시, 난이도를 지정하고 해당 문제에 대한 영상 촬영 플로우로 넘어갑니다.")
    @PostMapping
    fun register(
        @PathVariable storyId: Long,
        @RequestBody @Valid request: ProblemRequest,
    ): ClogApiResponse<SaveProblemResponse> {
        val result = saveProblem(storyId, request)
        return ClogApiResponse.from(result)
    }
}
