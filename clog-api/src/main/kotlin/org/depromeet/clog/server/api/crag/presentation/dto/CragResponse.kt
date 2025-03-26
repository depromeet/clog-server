package org.depromeet.clog.server.api.crag.presentation.dto

import io.swagger.v3.oas.annotations.media.Schema
import org.depromeet.clog.server.domain.crag.domain.Crag

@Schema(description = "암장 정보 응답 DTO")
data class CragResponse(
    @Schema(description = "암장 id", example = "1")
    val id: Long,
    @Schema(description = "암장명", example = "강남 클라이밍 파크")
    val name: String,
    @Schema(description = "도로명 주소", example = "서울특별시 강남구 테헤란로 152")
    val roadAddress: String
) {

    companion object {
        fun from(crag: Crag): CragResponse {
            return CragResponse(
                id = crag.id!!,
                name = crag.name,
                roadAddress = crag.validateRoadAddressNotBlank()
            )
        }
    }
}
