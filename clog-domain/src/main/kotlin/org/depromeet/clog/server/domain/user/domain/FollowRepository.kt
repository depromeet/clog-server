package org.depromeet.clog.server.domain.user.domain

interface FollowRepository {

    fun findFollowersByUserId(userId: Long): List<OtherUser>

    fun findFollowingByUserId(userId: Long): List<OtherUser>
}
