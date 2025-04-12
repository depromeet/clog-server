package org.depromeet.clog.server.domain.crag.domain.color

interface ColorRepository {

    fun findDistinctColorsByUserId(userId: Long, cursor: Long?, pageSize: Int): List<Color>
}
