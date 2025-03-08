package org.depromeet.clog.server.domain.thumbnail.infrastructure.thumbnail

import org.depromeet.clog.server.domain.thumbnail.domain.Thumbnail

interface ThumbnailRepository {
    fun save(thumbnail: Thumbnail): Thumbnail
}
