package org.depromeet.clog.server.global.utils.dto

import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "커서 기반 페이지네이션을 위한 요청 DTO")
data class CursorPageRequest(
    @Parameter(example = "100000")
    val cursor: Long? = null,

    @Parameter(example = "10")
    val pageSize: Int = 10
)
