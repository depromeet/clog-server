package org.depromeet.clog.server.infrastructure.video

import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.jpa.repository.Modifying as Modifying1

interface VideoJpaRepository : JpaRepository<VideoEntity, Long> {

    @Modifying1
    @Transactional
    @Query(
        """
        update VideoEntity v set v.thumbnailUrl = :thumbnailUrl where v.id = :videoId and v.thumbnailUrl <> :thumbnailUrl
        """
    )
    fun updateThumbnailUrl(
        @Param("videoId") videoId: Long,
        @Param("thumbnailUrl") thumbnailUrl: String
    ): Int
}
