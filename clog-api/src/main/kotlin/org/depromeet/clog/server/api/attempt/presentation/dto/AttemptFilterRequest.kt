package org.depromeet.clog.server.api.attempt.presentation.dto

import org.depromeet.clog.server.domain.attempt.AttemptStatus

data class AttemptFilterRequest(
    val attemptStatus: AttemptStatus? = null,
    val cragId: Long? = null,
    val colorId: Long? = null,

    @Deprecated("난이도 id가 아닌 색상 id를 활용해주세요.")
    val gradeId: Long? = null,
)
