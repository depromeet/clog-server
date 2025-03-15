package org.depromeet.clog.server.global.utils.dto

data class CursorPageRequest(
    val cursor: Long? = null,
    val pageSize: Int = 10
)
