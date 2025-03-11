package org.depromeet.clog.server.admin.api.presentation.dto

object SaveCragColor {

    data class Request(
        val name: String = "",
        val hex: String = ""
    )

    data class Response(
        val name: String,
        val hex: String
    )
}
