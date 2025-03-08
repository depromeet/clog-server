package org.depromeet.clog.server.infrastructure.video

import jakarta.persistence.*
import org.depromeet.clog.server.domain.video.Video
import org.depromeet.clog.server.infrastructure.common.BaseEntity

@Table(name = "video")
@Entity
class VideoEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = null,

    @Column(name = "local_path")
    val localPath: String,

    @Column(name = "thumbnail_url")
    val thumbnailUrl: String,

    @Column(name = "duration_ms")
    val durationMs: Long,
) : BaseEntity() {
    fun toDomain(): Video {
        return Video(
            id = id,
            localPath = localPath,
            thumbnailUrl = thumbnailUrl,
            durationMs = durationMs,
            createdAt = createdAt,
            modifiedAt = modifiedAt,
        )
    }

    companion object {
        fun fromDomain(video: Video): VideoEntity {
            return VideoEntity(
                id = video.id,
                localPath = video.localPath,
                thumbnailUrl = video.thumbnailUrl,
                durationMs = video.durationMs,
            )
        }
    }
}
