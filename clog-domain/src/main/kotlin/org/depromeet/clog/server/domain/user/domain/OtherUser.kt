package org.depromeet.clog.server.domain.user.domain

data class OtherUser(
    val id: Long,
    val name: String? = null,
    val height: Double? = null,
    val armSpan: Double? = null,
    val instagramUrl: String? = null,
    val isFollowing: Boolean,
)
