package org.depromeet.clog.server.domain.crag.domain.grade

interface GradeRepository {

    fun findById(id: Long): Grade?

    fun findDistinctGradesByUserId(
        userId: Long,
        cursor: Long?,
        pageSize: Int
    ): List<Grade>

    fun findGradesByCragId(cragId: Long): List<Grade>
}
