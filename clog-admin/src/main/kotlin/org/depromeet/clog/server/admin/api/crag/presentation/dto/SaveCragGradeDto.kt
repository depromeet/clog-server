package org.depromeet.clog.server.admin.api.crag.presentation.dto

object SaveCragGradeDto {

    fun initForm(): Request {
        return Request("", "", "")
    }

    data class Request(
//        @field:NotBlank(message = "crag color name must not be blank")
        val colorName: String,

//        @field:NotBlank(message = "crag color hex must not be blank")
        val colorHex: String,

//        @field:NotBlank(message = "crag grade order must not be blank")
        val gradeOrder: String
    )

    data class Response(
        val colorName: String,
        val colorHex: String,
        val gradeOrder: Int?
    )
}
