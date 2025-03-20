package org.depromeet.clog.server.infrastructure.thumbnail

import org.depromeet.clog.server.domain.thumbnail.Thumbnail
import org.depromeet.clog.server.domain.thumbnail.ThumbnailRepository
import org.springframework.stereotype.Component

@Component
class ThumbnailAdapter(
    private val thumbnailJpaRepository: ThumbnailJpaRepository
) : ThumbnailRepository {

    override fun save(thumbnail: Thumbnail): Thumbnail {
        val entity = ThumbnailEntity.fromDomain(thumbnail)
        val savedEntity = thumbnailJpaRepository.save(entity)
        return savedEntity.toDomain()
    }
}
