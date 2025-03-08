package org.depromeet.clog.server.infrastructure.thumbnail

import org.depromeet.clog.server.domain.thumbnail.domain.Thumbnail
import org.depromeet.clog.server.domain.thumbnail.infrastructure.thumbnail.ThumbnailRepository
import org.springframework.stereotype.Component

@Component
class ThumbnailRepositoryAdapter(
    private val thumbnailJpaRepository: ThumbnailJpaRepository
) : ThumbnailRepository {
    override fun save(thumbnail: Thumbnail): Thumbnail {
        val entity = ThumbnailEntity.fromDomain(thumbnail)
        val savedEntity = thumbnailJpaRepository.save(entity)
        return savedEntity.toDomain()
    }
}
