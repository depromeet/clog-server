package org.depromeet.clog.server.admin.api.presentation.dto

object SaveCragGrade {

    data class Request(
        val colorName: String = "",
        val colorHex: String = "",
        val gradeOrder: String = ""
    )

    data class Response(
        val colorName: String,
        val colorHex: String,
        val gradeOrder: Int?
    )
}
