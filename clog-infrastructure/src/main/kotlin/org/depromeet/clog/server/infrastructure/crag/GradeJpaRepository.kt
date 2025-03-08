package org.depromeet.clog.server.infrastructure.crag

import org.springframework.data.jpa.repository.JpaRepository

interface GradeJpaRepository : JpaRepository<GradeEntity, Long>
