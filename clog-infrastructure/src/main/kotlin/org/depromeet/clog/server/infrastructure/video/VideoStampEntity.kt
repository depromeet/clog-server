package org.depromeet.clog.server.infrastructure.video

import jakarta.persistence.*
import org.depromeet.clog.server.domain.video.VideoStamp
import org.depromeet.clog.server.infrastructure.common.BaseEntity

@Table(name = "video_stamp")
@Entity
class VideoStampEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = null,

    @Column(name = "video_id")
    val videoId: Long,

    @Column(name = "time_ms")
    val timeMs: Long,
) : BaseEntity() {
    fun toDomain(): VideoStamp {
        return VideoStamp(
            id = id,
            timeMs = timeMs,
            videoId = videoId,
            createdAt = createdAt,
            modifiedAt = modifiedAt,
        )
    }

    companion object {
        fun fromDomain(videoStamp: VideoStamp): VideoStampEntity {
            return VideoStampEntity(
                id = videoStamp.id,
                videoId = videoStamp.videoId,
                timeMs = videoStamp.timeMs,
            )
        }
    }
}
