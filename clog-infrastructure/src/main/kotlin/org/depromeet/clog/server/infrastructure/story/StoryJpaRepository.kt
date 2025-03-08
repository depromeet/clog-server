package org.depromeet.clog.server.infrastructure.story

import org.springframework.data.jpa.repository.JpaRepository

interface StoryJpaRepository : JpaRepository<StoryEntity, Long>
