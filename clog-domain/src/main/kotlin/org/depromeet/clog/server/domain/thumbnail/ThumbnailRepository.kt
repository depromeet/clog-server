package org.depromeet.clog.server.domain.thumbnail

interface ThumbnailRepository {
    fun save(thumbnail: Thumbnail): Thumbnail
}
