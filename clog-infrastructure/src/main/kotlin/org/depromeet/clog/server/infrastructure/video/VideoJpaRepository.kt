package org.depromeet.clog.server.infrastructure.video

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface VideoJpaRepository : JpaRepository<VideoEntity, Long> {
    @Query("SELECT v FROM AttemptEntity a JOIN a.video v WHERE a.problem.id = :problemId ORDER BY v.id DESC")
    fun findByProblemIdOrderByIdDesc(@Param("problemId") problemId: Long): List<VideoEntity>
}
