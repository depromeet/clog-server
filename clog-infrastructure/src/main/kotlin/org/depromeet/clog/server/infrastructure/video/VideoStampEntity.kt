package org.depromeet.clog.server.infrastructure.video

import jakarta.persistence.*
import org.depromeet.clog.server.infrastructure.common.BaseEntity

@Table(name = "video_stamp")
@Entity
class VideoStampEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id")
    val video: VideoEntity,

    @Column(name = "time_ms")
    val timeMs: Long,
) : BaseEntity()
