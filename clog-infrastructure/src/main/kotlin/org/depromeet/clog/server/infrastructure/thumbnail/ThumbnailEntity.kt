package org.depromeet.clog.server.infrastructure.thumbnail

import jakarta.persistence.*
import org.depromeet.clog.server.domain.thumbnail.Thumbnail
import org.depromeet.clog.server.infrastructure.common.BaseEntity

@Entity
@Table(name = "thumbnail")
class ThumbnailEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val fileUrl: String
) : BaseEntity() {
    fun toDomain(): Thumbnail = Thumbnail(
        id = id,
        fileUrl = fileUrl
    )

    companion object {
        fun fromDomain(thumbnail: Thumbnail): ThumbnailEntity {
            return ThumbnailEntity(
                id = thumbnail.id,
                fileUrl = thumbnail.fileUrl
            )
        }
    }
}
