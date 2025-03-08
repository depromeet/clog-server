package org.depromeet.clog.server.domain.crag.domain

interface GradeRepository {

    fun findById(id: Long): Grade?
}
