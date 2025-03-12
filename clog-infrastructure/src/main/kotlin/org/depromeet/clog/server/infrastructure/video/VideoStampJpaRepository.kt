package org.depromeet.clog.server.infrastructure.video

import org.springframework.data.jpa.repository.JpaRepository

interface VideoStampJpaRepository : JpaRepository<VideoStampEntity, Long> {

    fun deleteAllByVideoId(videoId: Long)
}
