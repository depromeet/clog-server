package org.depromeet.clog.server.admin.api.crag.presentation.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

object SaveCragColorDto {

    fun initForm(): Request {
        return Request("", "")
    }

    data class Request(
        @field:NotBlank(message = "색상 이름/hex code를 입력해 주세요.")
        val name: String,

        @field:NotBlank(message = "색상 이름/hex code를 입력해 주세요.")
        @field:Size(min = 7, max = 7, message = "hex code must be exactly 7 characters long")
        val hex: String
    )

    data class Response(
        val name: String,
        val hex: String
    )
}
