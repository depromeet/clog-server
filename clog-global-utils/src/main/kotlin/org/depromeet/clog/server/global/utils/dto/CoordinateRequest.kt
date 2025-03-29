package org.depromeet.clog.server.global.utils.dto

import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "위도/경도 요청")
data class CoordinateRequest(
    @Parameter(description = "사용자의 경도(x)", example = "122.4194")
    val longitude: Double?,

    @Parameter(description = "사용자의 위도(y)", example = "37.7749")
    val latitude: Double?
) {

    val longitudeOrDefault: Double
        get() = longitude ?: 127.058089608702

    val latitudeOrDefault: Double
        get() = latitude ?: 37.5423101113247
}
