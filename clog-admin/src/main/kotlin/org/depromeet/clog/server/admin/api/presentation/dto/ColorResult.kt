package org.depromeet.clog.server.admin.api.presentation.dto

import org.depromeet.clog.server.domain.crag.domain.color.Color

data class ColorResult(
    val name: String,
    val hex: String
) {

    companion object {
        fun from(color: Color): ColorResult {
            return ColorResult(
                name = color.name,
                hex = color.hex
            )
        }
    }
}
