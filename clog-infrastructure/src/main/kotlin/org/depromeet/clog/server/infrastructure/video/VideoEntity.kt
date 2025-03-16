package org.depromeet.clog.server.infrastructure.video

import jakarta.persistence.*
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

    @OneToMany(mappedBy = "video", fetch = FetchType.LAZY)
    val stamps: List<VideoStampEntity> = emptyList(),
) : BaseEntity()
