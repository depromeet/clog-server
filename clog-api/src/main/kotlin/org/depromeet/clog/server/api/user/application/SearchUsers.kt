package org.depromeet.clog.server.api.user.application

import org.depromeet.clog.server.api.user.presentation.UserResponse
import org.depromeet.clog.server.domain.user.domain.UserQuery
import org.depromeet.clog.server.domain.user.domain.UserRepository
import org.depromeet.clog.server.global.utils.dto.CursorPagination
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SearchUsers(
    private val userRepository: UserRepository,
) {

    @Transactional(readOnly = true)
    operator fun invoke(
        userId: Long,
        query: UserQuery,
    ): CursorPagination.Response<Long, UserResponse> {
        val users = if (query.keyword == null) {
            emptyList()
        } else {
            userRepository.findAllActiveUsersByQuery(
                requestedUserId = userId,
                query = query,
            )
        }

        return CursorPagination.Response.of(
            results = users.map { UserResponse.from(it) },
            pageSize = query.pageSize,
            cursorExtractor = { it.id }
        )
    }
}
