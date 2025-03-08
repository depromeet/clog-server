package org.depromeet.clog.server.infrastructure.thumbnail

import org.springframework.data.jpa.repository.JpaRepository

interface ThumbnailJpaRepository : JpaRepository<ThumbnailEntity, Long>
