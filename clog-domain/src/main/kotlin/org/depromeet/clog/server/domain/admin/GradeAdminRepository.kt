package org.depromeet.clog.server.domain.admin

import org.depromeet.clog.server.domain.crag.domain.Crag
import org.depromeet.clog.server.domain.crag.domain.grade.Grade

interface GradeAdminRepository {

    fun save(grade: Grade): Grade

    fun findByCrag(crag: Crag): List<Grade>
}
