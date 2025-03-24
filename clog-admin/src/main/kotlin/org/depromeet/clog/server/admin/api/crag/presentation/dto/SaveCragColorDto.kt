package org.depromeet.clog.server.admin.api.crag.presentation.dto

object SaveCragColorDto {

    fun initForm(): Request {
        return Request("", "")
    }

    data class Request(
//        @field:NotBlank(message = "crag color name must not be blank")
        val name: String,

//        @field:NotBlank(message = "crag color hex must not be blank")
        val hex: String
    )

    data class Response(
        val name: String,
        val hex: String
    )
}
